package com.pandacorp.togetheraichat.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.togetheraichat.domain.repository.ChatsRepository
import com.pandacorp.togetheraichat.presentation.ui.adapter.chat.ChatItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatsViewModel(private val repository: ChatsRepository) : ViewModel() {
    val chatsFlow = repository.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addChat(chatItem: ChatItem) {
        CoroutineScope(Dispatchers.IO).launch {
            chatItem.id = repository.insert(chatItem)
        }
    }

    fun deleteChat(chatItem: ChatItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(chatItem)
        }
    }
}