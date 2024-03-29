package com.pandacorp.lechat.domain.repository

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.presentation.ui.adapter.suggestions.SuggestionItem
import kotlinx.coroutines.flow.Flow

interface TogetherRepository {
    fun getResponse(
        messages: List<MessageItem>,
        model: String,
        temperature: Float,
        maxTokens: Int?,
        frequencyPenalty: Float?,
        topP: Float?
    ): Flow<ChatCompletionChunk>

    suspend fun summarizeChat(messages: List<MessageItem>): String
    suspend fun getSuggestions(messages: List<MessageItem>): List<SuggestionItem>
}