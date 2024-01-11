package com.pandacorp.togetheraichat.data.repository

import com.pandacorp.togetheraichat.data.database.dao.ChatsDao
import com.pandacorp.togetheraichat.data.mapper.ChatsMapper
import com.pandacorp.togetheraichat.domain.repository.ChatsRepository
import com.pandacorp.togetheraichat.presentation.ui.adapter.chat.ChatItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatsRepositoryImpl(private val dao: ChatsDao, private val mapper: ChatsMapper) : ChatsRepository {
    override fun getAll(): Flow<List<ChatItem>> = dao.getAll().map { flow ->
        flow.map { item ->
            mapper.toChatItem(item)
        }.toMutableList()
    }

    override fun insert(item: ChatItem) = dao.insert(mapper.toChatDataItem(item))

    override fun delete(item: ChatItem) = dao.delete(mapper.toChatDataItem(item))

    override fun update(item: ChatItem) = dao.update(mapper.toChatDataItem(item))
}