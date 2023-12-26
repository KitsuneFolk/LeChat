package com.pandacorp.togetheraichat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.togetheraichat.domain.model.MessageItem
import kotlinx.coroutines.launch

class MessagesViewModel : ViewModel() {
    val messagesList: MutableLiveData<MutableList<MessageItem>> = MutableLiveData(mutableListOf())

    fun addMessage(messageItem: MessageItem) {
        viewModelScope.launch {
            messagesList.value?.add(messageItem)
            messagesList.postValue(messagesList.value)
        }
    }
}