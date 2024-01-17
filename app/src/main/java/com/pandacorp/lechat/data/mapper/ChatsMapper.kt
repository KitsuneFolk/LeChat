package com.pandacorp.lechat.data.mapper

import com.pandacorp.lechat.data.model.ChatDataItem
import com.pandacorp.lechat.presentation.ui.adapter.chat.ChatItem

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