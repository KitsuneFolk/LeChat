package com.pandacorp.lechat.domain.repository

import com.pandacorp.lechat.presentation.ui.adapter.chat.ChatItem
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    fun getAll(): Flow<List<ChatItem>>
    fun insert(item: ChatItem): Long
    fun delete(item: ChatItem)
    fun update(item: ChatItem)
}