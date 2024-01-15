package com.pandacorp.togetheraichat.data.mapper

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.pandacorp.togetheraichat.domain.model.MessageItem

class MessagesMapper {
    fun toChatMessages(list: List<MessageItem>): List<ChatMessage> {
        return list.map {
            val role = ChatRole(
                when (it.role) {
                    MessageItem.SYSTEM -> ChatRole.System.role
                    MessageItem.USER -> ChatRole.User.role
                    MessageItem.AI -> ChatRole.Assistant.role
                    else -> throw IllegalArgumentException("Unknown role: ${it.role}")
                }
            )
            ChatMessage(role = role, content = it.message)
        }
    }

    fun toSummarizationMessages(messages: List<MessageItem>): String {
        var stringMessages = ""
        messages.map { messageItem ->
            val roleName = when (messageItem.role) {
                0 -> "System"
                1 -> "User"
                2 -> "AI"
                else -> "Unknown Role"
            }

            stringMessages += "$roleName: ${messageItem.message}\n"
        }
        return stringMessages
    }
}