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

    fun addMessage(messageItem: MessageItem): Int {
        messageItem.id = (messagesList.value!!.size).toLong()
        messagesList.value?.add(messageItem)
        messagesList.postValue(messagesList.value)
        return messageItem.id.toInt()
    }

    fun getResponse(onTokenReceived: (String?) -> Unit) {
        viewModelScope.launch {
            val lastUserMessage = messagesList.value?.last { it.role == MessageItem.USER }!!
            repository.getResponse(lastUserMessage.message)
                .onEach {
                    onTokenReceived(it.choices[0].delta.content)
                }
                .collect()
        }
    }

    fun replaceAt(position: Int, replacement: MessageItem) {
        messagesList.value!![position] = replacement
        messagesList.postValue(messagesList.value)
    }
}