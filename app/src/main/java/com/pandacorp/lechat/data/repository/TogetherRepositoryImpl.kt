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
import com.pandacorp.lechat.presentation.ui.adapter.suggestions.SuggestionItem
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
            You are a professional title creator bot. Create a title for this conversation in the user's language. Respond only with the title and nothing more. You are prohibited from using words not related to the title(e.g "Certainly", "Sure", "Title: ").
            Example:
              User: What's the capital of Ukraine?
              AI: The capital of Ukraine is Kyiv. Kyiv is an important industrial, scientific, educational, and cultural center in Eastern Europe.
              Good title: The capital of Ukraine.
              Bad title: "Title: The capital of Ukraine is Kyiv, which is an important center of Easter Europe."
            Conversation:
            "
            $mappedMessages"
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
            summarized = summarized.replace("Title: ", "")
            summarized = summarized.trim() // Models return a whitespace character at the start of the response
            continuation.resume(summarized)
        }
    }

    override suspend fun getSuggestions(messages: List<MessageItem>): List<SuggestionItem> =
        suspendCoroutine { continuation ->
            val mappedMessages = messagesMapper.toSummarizationMessages(messages)
            val suggestionsPrompt = """
                You specialize in crafting engaging questions to keep conversations flowing from the User's perspective. Generate THREE questions in the user's language that they might ask, and haven't been addressed by the AI. Begin each question with "User: ", keeping them concise and straightforward, separate each question with a new line. DO NOT answer your own questions.
    
                Example:
                ```
                User: What ingredients are needed for a Neapolitan pizza?
                AI: Neapolitan pizza requires tomato sauce, mozzarella cheese, and fresh basil leaves.
                Your suggestions:
                User: What distinguishes Neapolitan pizza from Margherita pizza?
                User: What temperature is ideal for baking a homemade pizza?
                User: Can you suggest an easy alternative to tomato sauce for pizza?
                ```
                
                Conversation:
                ```
                $mappedMessages
                ```
                Now generate ONLY THREE interesting questions about the conversation's topic.
            """

            var suggestionsString = ""
            val summarizeMessage = listOf(
                MessageItem(
                    id = 0,
                    role = MessageItem.USER,
                    message = suggestionsPrompt
                )
            )
            @OptIn(DelicateCoroutinesApi::class)
            GlobalScope.launch {
                getResponse(summarizeMessage, "mistralai/Mistral-7B-Instruct-v0.2", 0.3f, 200, 0f, 0f)
                    .onEach {
                        suggestionsString += it.choices[0].delta.content
                    }
                    .toList() // Use toList to collect the entire flow
                // Remove quotes from the summary
                suggestionsString = suggestionsString.replace("\"", "")
                suggestionsString = suggestionsString.replace("\'", "")
                // Remove "User: " from the summary, we need it otherwise AI will have create questions and answers itself
                suggestionsString = suggestionsString.replace("User: ", "")
                suggestionsString =
                    suggestionsString.trim() // Models return a whitespace character at the start of the response
                var suggestionsList = suggestionsString.split("\n").map {
                    SuggestionItem(text = it.trim())
                }
                suggestionsList = suggestionsList.subList(0, 3) // Limit to 3
                continuation.resume(suggestionsList)
            }
        }
}