package com.pandacorp.togetheraichat.domain.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.pandacorp.togetheraichat.domain.model.MessageItem
import kotlinx.coroutines.flow.Flow

interface TogetherRepository {
    fun getResponse(messages: MutableList<MessageItem>, temperature: Double): Flow<ChatCompletionChunk>
}