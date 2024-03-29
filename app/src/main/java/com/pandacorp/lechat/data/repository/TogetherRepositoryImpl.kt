package com.pandacorp.lechat.data.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.OpenAIHost
import com.aallam.openai.client.RetryStrategy
import com.pandacorp.lechat.data.mapper.MessagesMapper
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.domain.repository.TogetherRepository
import com.pandacorp.lechat.presentation.ui.adapter.suggestions.SuggestionItem
import com.pandacorp.lechat.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
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
        // Configuration needed to run openai-kotlin
        val client = HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                }
            }
        }
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
            messages = mappedMessages,
            temperature = temperature.toDouble(),
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
            if (summarized.last() == '.') summarized = summarized.dropLast(1) // Remove the final dot
            summarized = summarized.trim() // Models return a whitespace character at the start of the response
            continuation.resume(summarized)
        }
    }

    override suspend fun getSuggestions(messages: List<MessageItem>): List<SuggestionItem> =
        suspendCoroutine { continuation ->
            val mappedMessages = messagesMapper.toSummarizationMessages(messages)
            val suggestionsPrompt = """
                API message: You are a professional questions composer from the User's perspective. Please generate 3 questions in the user's language that they might ask, and haven't been addressed by the AI. Keep them concise and straightforward, separate each question with a new line. Please only generate questions, no words of acknowledgement, don't write numbers of questions nor use dashes.
                
                Good Example: "Why is the sky blue?"
                Bad Example: "1. Why is the sky blue?"
                
                Conversation:
                ```
                $mappedMessages
                ```
                Now generate three interesting questions from the user's perspective.
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
                getResponse(summarizeMessage, "mistralai/Mixtral-8x7B-Instruct-v0.1", 0.7f, 200, null, null)
                    .onEach {
                        suggestionsString += it.choices[0].delta.content
                    }
                    .toList() // Use toList to collect the entire flow
                // Remove quotes from the summary
                suggestionsString = suggestionsString.replace("\"", "")
                suggestionsString = suggestionsString.replace("\'", "")
                // Can't make mixtral don't include numbers
                suggestionsString = suggestionsString.replaceFirst("1. ", "")
                suggestionsString = suggestionsString.replaceFirst("2. ", "")
                suggestionsString = suggestionsString.replaceFirst("3. ", "")
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