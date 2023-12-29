package com.pandacorp.togetheraichat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: TogetherRepository) : ViewModel() {
    val messagesList: MutableLiveData<MutableList<MessageItem>> = MutableLiveData(mutableListOf())

    fun addMessage(messageItem: MessageItem) {
        viewModelScope.launch {
            messagesList.value?.add(messageItem)
            messagesList.postValue(messagesList.value)
        }
    }

    fun getResponse(onTokenReceived: (String?) -> Unit) {
        viewModelScope.launch {
            repository.getResponse()
                .onEach {
                    onTokenReceived(it.choices[0].delta.content)
                }
                .collect()
        }
    }
}