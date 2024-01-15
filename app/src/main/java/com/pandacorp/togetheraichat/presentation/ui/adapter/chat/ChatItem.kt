package com.pandacorp.togetheraichat.presentation.ui.adapter.chat

import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.utils.Constants

data class ChatItem(
    var id: Long = 0,
    val title: String = "New Chat",
    val messages: List<MessageItem> = Constants.defaultMessagesList
)