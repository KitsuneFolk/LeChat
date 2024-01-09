package com.pandacorp.togetheraichat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pandacorp.togetheraichat.data.database.dao.ChatsDao
import com.pandacorp.togetheraichat.data.model.ChatDataItem

@Database(entities = [ChatDataItem::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
}