package com.pandacorp.togetheraichat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pandacorp.togetheraichat.data.mapper.Converters
import com.pandacorp.togetheraichat.domain.model.MessageItem

@Entity(tableName = "chats_table")
@TypeConverters(Converters::class)
data class ChatDataItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "messages") val messages: List<MessageItem>
)