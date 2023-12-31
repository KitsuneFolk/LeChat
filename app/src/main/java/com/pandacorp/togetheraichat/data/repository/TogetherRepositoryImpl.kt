package com.pandacorp.togetheraichat.data.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.OpenAIHost
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.utils.Constants
import kotlinx.coroutines.flow.Flow

class TogetherRepositoryImpl : TogetherRepository {
    override fun getResponse(message: String): Flow<ChatCompletionChunk> {
        val host = OpenAIHost(
            baseUrl = "https://api.together.xyz",
        )
        val config = OpenAIConfig(
            host = host,
            token = Constants.apiKey.value!!,
        )
        val openAI = OpenAI(config)

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("mistralai/Mixtral-8x7B-Instruct-v0.1"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "You are a helpful assistant!"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = message
                )
            )
        )
        return openAI.chatCompletions(chatCompletionRequest)
    }
}