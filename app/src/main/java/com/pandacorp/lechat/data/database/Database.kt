package com.pandacorp.lechat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pandacorp.lechat.data.database.dao.ChatsDao
import com.pandacorp.lechat.data.model.ChatDataItem

@Database(entities = [ChatDataItem::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
}