package com.pandacorp.lechat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.lechat.domain.repository.ChatsRepository
import com.pandacorp.lechat.presentation.ui.adapter.chat.ChatItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatsViewModel(private val repository: ChatsRepository) : ViewModel() {
    val chatsFlow = repository.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val currentChat = MutableLiveData<ChatItem?>(null)

    fun addChat(chatItem: ChatItem = ChatItem()) {
        CoroutineScope(Dispatchers.IO).launch {
            chatItem.id = repository.insert(chatItem)
            currentChat.postValue(chatItem)
        }
    }

    fun deleteChat(chatItem: ChatItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(chatItem)
            if (chatItem.id == currentChat.value?.id) {
                currentChat.postValue(null)
            }
        }
    }

    fun updateChat(chatItem: ChatItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.update(chatItem)
        }
    }
}