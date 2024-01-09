package com.pandacorp.togetheraichat.presentation.ui.adapter.chat

import com.pandacorp.togetheraichat.domain.model.MessageItem

data class ChatItem(var id: Long = 0, val title: String = "", val messages: List<MessageItem>)