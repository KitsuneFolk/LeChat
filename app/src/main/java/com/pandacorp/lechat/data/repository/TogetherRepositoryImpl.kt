package com.pandacorp.lechat.data.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.OpenAIHost
import com.aallam.openai.client.RetryStrategy
import com.pandacorp.lechat.client
import com.pandacorp.lechat.data.mapper.MessagesMapper
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.domain.repository.TogetherRepository
import com.pandacorp.lechat.utils.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TogetherRepositoryImpl(private val messagesMapper: MessagesMapper) : TogetherRepository {
    override fun getResponse(
        messages: List<MessageItem>,
        model: String,
        temperature: Float,
        maxTokens: Int?,
        frequencyPenalty: Float?,
        topP: Float?
    ): Flow<ChatCompletionChunk> {
        val host = OpenAIHost(baseUrl = "https://api.together.xyz")
        val config = OpenAIConfig(
            host = host,
            token = Constants.apiKey.value!!,
            retry = RetryStrategy(maxRetries = 0),
            engine = client.engine
        )
        val openAI = OpenAI(config)

        val mappedMessages = messagesMapper.toChatMessages(messages)
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(model),
            temperature = temperature.toDouble(),
            maxTokens = if (maxTokens == 0) null else maxTokens,
            messages = mappedMessages,
            frequencyPenalty = frequencyPenalty?.toDouble(),
            topP = topP?.toDouble()
        )
        return openAI.chatCompletions(chatCompletionRequest)
    }

    override suspend fun summarizeChat(messages: List<MessageItem>): String = suspendCoroutine { continuation ->
        val mappedMessages = messagesMapper.toSummarizationMessages(messages)
        val summarizationPrompt =
            """"
            Summarize this chat in a three to six words, don't use redundant words("Sure", "Certainly", etc), provide only summarization. This is very important to my career.
            Example 1:
              User: What's the capital of Ukraine?
              AI: The capital of Ukraine is Kyiv. Kyiv is an important industrial, scientific, educational, and cultural center in Eastern Europe.
              Good Summary: The capital of Ukraine.
              Bad Summary: The capital of Ukraine is Kyiv, which is an important center of Easter Europe.
            Example 2:
              User: Where is Monaco located?
              AI: Monaco is a principality located in Western Europe, on the French Riviera in the Mediterranean Sea. It is bordered by France to the north, east, and west, and by the Mediterranean Sea to the south.
              Good Summary: Monaco is located in Western Europe.
              Bad Summary: Monaco is a small principality located in Western Europe on the French Riviera. It is known for its luxurious lifestyle.
            Conversation:
              "$mappedMessages"
            """
        var summarized = ""
        val summarizeMessage = listOf(
            MessageItem(
                id = 0,
                role = MessageItem.USER,
                message = summarizationPrompt
            )
        )
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch {
            getResponse(summarizeMessage, "mistralai/Mistral-7B-Instruct-v0.1", 0f, 30, 0f, 0f)
                .onEach {
                    summarized += it.choices[0].delta.content
                }
                .toList() // Use toList to collect the entire flow
            // Remove quotes from the summary
            summarized = summarized.replace("\"", "")
            summarized = summarized.replace("\'", "")
            summarized = summarized.trim() // Models return a whitespace character at the start of the response
            continuation.resume(summarized)
        }
    }
}