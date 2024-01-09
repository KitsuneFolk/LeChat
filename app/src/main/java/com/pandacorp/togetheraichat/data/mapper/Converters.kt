package com.pandacorp.togetheraichat.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pandacorp.togetheraichat.domain.model.MessageItem
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromMessageItemList(messageItemList: List<MessageItem>?): String {
        if (messageItemList == null) {
            return ""
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MessageItem>>() {}.type
        return gson.toJson(messageItemList, type)
    }

    @TypeConverter
    fun toMessageItemList(messageItemListString: String?): List<MessageItem> {
        if (messageItemListString.isNullOrEmpty()) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MessageItem>>() {}.type
        return gson.fromJson(messageItemListString, type)
    }
}
