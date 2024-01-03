package com.pandacorp.togetheraichat.data.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.OpenAIHost
import com.aallam.openai.client.RetryStrategy
import com.pandacorp.togetheraichat.client
import com.pandacorp.togetheraichat.data.mapper.MessagesMapper
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.utils.Constants
import kotlinx.coroutines.flow.Flow

class TogetherRepositoryImpl(private val messagesMapper: MessagesMapper) : TogetherRepository {
    override fun getResponse(messages: MutableList<MessageItem>): Flow<ChatCompletionChunk> {
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
            model = ModelId("mistralai/Mixtral-8x7B-Instruct-v0.1"),
            messages = mappedMessages
        )
        return openAI.chatCompletions(chatCompletionRequest)
    }
}