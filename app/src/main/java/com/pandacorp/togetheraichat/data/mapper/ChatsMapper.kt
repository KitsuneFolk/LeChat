package com.pandacorp.togetheraichat.data.mapper

import com.pandacorp.togetheraichat.data.model.ChatDataItem
import com.pandacorp.togetheraichat.presentation.ui.adapter.chat.ChatItem

class ChatsMapper {
    fun toChatDataItem(item: ChatItem): ChatDataItem {
        return ChatDataItem(
            id = item.id,
            title = item.title,
            messages = item.messages
        )
    }

    fun toChatItem(item: ChatDataItem): ChatItem {
        return ChatItem(
            id = item.id,
            title = item.title,
            messages = item.messages
        )
    }
}