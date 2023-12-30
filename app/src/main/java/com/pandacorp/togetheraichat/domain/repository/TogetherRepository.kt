package com.pandacorp.togetheraichat.domain.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import kotlinx.coroutines.flow.Flow

interface TogetherRepository {
    fun getResponse(message: String): Flow<ChatCompletionChunk>
}