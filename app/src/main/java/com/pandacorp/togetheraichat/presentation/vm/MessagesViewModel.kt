package com.pandacorp.togetheraichat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.exception.TogetherAIException
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.domain.repository.TogetherRepository
import com.pandacorp.togetheraichat.utils.Constants
import com.pandacorp.togetheraichat.utils.PreferenceHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: TogetherRepository) : ViewModel() {
    val messagesList: MutableLiveData<MutableList<MessageItem>> =
        MutableLiveData(Constants.defaultMessagesList.toMutableList())
    val errorCode = MutableLiveData<Int?>(null)

    var onResponseGenerated: ((List<MessageItem>) -> Unit)? = null

    fun addMessage(messageItem: MessageItem): Int {
        messageItem.id = (messagesList.value!!.size).toLong()
        messagesList.value?.add(messageItem)
        messagesList.postValue(messagesList.value)
        return messageItem.id.toInt()
    }

    fun getResponse() {
        viewModelScope.launch {
            try {
                var isFirstTime = true
                repository.getResponse(
                    messagesList.value!!,
                    PreferenceHandler.modelValue,
                    PreferenceHandler.temperature,
                    PreferenceHandler.maxTokens,
                    PreferenceHandler.frequencyPenalty,
                    PreferenceHandler.topP
                )
                    .onEach {
                        var token = it.choices[0].delta.content
                        if (isFirstTime) {
                            val response = MessageItem(message = "", role = MessageItem.AI)
                            addMessage(response)
                            isFirstTime = false
                            // Make sure the first token is not a space
                            token = token?.trim()
                        }
                        val response = messagesList.value!!.last().copy()
                        response.message += token
                        replaceAt(response.id.toInt(), response)
                    }
                    .onCompletion {
                        onResponseGenerated?.invoke(messagesList.value!!)
                    }
                    .collect()
            } catch (e: TogetherAIException) {
                val error = e.cause?.toString() ?: "\"Text: Unknown error\""
                val regex = Regex("\\b(\\d{3})\\b")
                val matchResult = regex.find(error)

                val errorCode = matchResult?.groupValues?.get(1)?.toIntOrNull()
                this@MessagesViewModel.errorCode.postValue(errorCode)
            }
        }
    }

    suspend fun summarizeChat(messages: List<MessageItem>): String {
        return repository.summarizeChat(messages)
    }

    private fun replaceAt(position: Int, replacement: MessageItem) {
        messagesList.value!![position] = replacement
        messagesList.postValue(messagesList.value)
    }
}