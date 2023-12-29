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
import kotlinx.coroutines.flow.Flow

class TogetherRepositoryImpl(private val apiKey: String) : TogetherRepository {
    override fun getResponse(): Flow<ChatCompletionChunk> {
        val host = OpenAIHost(
            baseUrl = "https://api.together.xyz",
        )
        val config = OpenAIConfig(
            host = host,
            token = apiKey,
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
                    content = "Hello!"
                )
            )
        )
        return openAI.chatCompletions(chatCompletionRequest)
    }
}