package com.pandacorp.lechat.presentation.ui.adapter.chat

import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.utils.Constants

data class ChatItem(
    var id: Long = 0,
    val title: String = "New Chat",
    val messages: List<MessageItem> = Constants.defaultMessagesList
)