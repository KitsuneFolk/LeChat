package com.pandacorp.togetheraichat.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pandacorp.togetheraichat.data.repository.TogetherRepositoryImpl

class ViewModelFactory(private val apiKey: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessagesViewModel(TogetherRepositoryImpl(apiKey)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}