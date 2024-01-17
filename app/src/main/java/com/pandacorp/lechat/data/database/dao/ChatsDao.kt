package com.pandacorp.lechat.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pandacorp.lechat.data.model.ChatDataItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatsDao {
    @Query("SELECT * FROM chats_table ORDER BY id DESC")
    fun getAll(): Flow<List<ChatDataItem>>

    @Insert
    fun insert(chatItem: ChatDataItem): Long

    @Delete
    fun delete(chatItem: ChatDataItem)

    @Update
    fun update(chatItem: ChatDataItem)
}