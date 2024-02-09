package com.pandacorp.lechat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.domain.repository.TogetherRepository
import com.pandacorp.lechat.presentation.ui.adapter.suggestions.SuggestionItem
import com.pandacorp.lechat.utils.Constants
import com.pandacorp.lechat.utils.PreferenceHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: TogetherRepository) : ViewModel() {
    val messagesList: MutableLiveData<MutableList<MessageItem>> =
        MutableLiveData(Constants.defaultMessagesList.toMutableList())
    val suggestionsList: MutableLiveData<List<SuggestionItem>> = MutableLiveData()
    val errorCode = MutableLiveData<Int?>(null)
    val isResponseGenerating = MutableLiveData(false)

    var onResponseGenerated: ((List<MessageItem>) -> Unit)? = null

    private var responseJob: Job? = null

    fun addMessage(messageItem: MessageItem): Int {
        messageItem.id = (messagesList.value!!.size).toLong()
        messagesList.value?.add(messageItem)
        messagesList.postValue(messagesList.value)
        return messageItem.id.toInt()
    }

    fun getResponse() {
        viewModelScope.launch {
            var isFirstTime = true
            responseJob = repository.getResponse(
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
                        isResponseGenerating.postValue(true)
                    }
                    val response = messagesList.value!!.last().copy()
                    response.message += token
                    replaceAt(response.id.toInt(), response)
                }
                .catch {
                    handleError(it)
                }
                .onCompletion {
                    isResponseGenerating.postValue(false)
                    responseJob = null
                    onResponseGenerated?.invoke(messagesList.value!!)
                }
                .launchIn(this)
        }
    }

    fun stopResponse() {
        responseJob?.cancel()
        responseJob = null
    }

    fun regenerateMessage(messageId: Long) {
        messagesList.value?.removeAll { it.id >= messageId }
        getResponse()
    }

    suspend fun summarizeChat(messages: List<MessageItem>): String {
        return repository.summarizeChat(messages)
    }

    suspend fun getSuggestions(messages: List<MessageItem>): List<SuggestionItem> {
        return repository.getSuggestions(messages)
    }

    private fun replaceAt(position: Int, replacement: MessageItem) {
        messagesList.value!![position] = replacement
        messagesList.postValue(messagesList.value)
    }

    private fun handleError(e: Throwable) {
        val message = e.cause?.toString() ?: "\"Text: Unknown error\""
        val regex = Regex("\\b(\\d{3})\\b")
        val matchResult = regex.find(message)

        val errorCode = matchResult?.groupValues?.get(1)?.toIntOrNull()
        this@MessagesViewModel.errorCode.postValue(errorCode)
    }
}