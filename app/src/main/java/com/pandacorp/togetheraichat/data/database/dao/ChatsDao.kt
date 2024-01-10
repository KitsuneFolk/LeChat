package com.pandacorp.togetheraichat.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pandacorp.togetheraichat.data.model.ChatDataItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatsDao {
    @Query("SELECT * FROM chats_table ORDER BY id DESC")
    fun getAll(): Flow<List<ChatDataItem>>

    @Insert
    fun insert(noteItem: ChatDataItem): Long

    @Delete
    fun delete(noteItem: ChatDataItem)
}