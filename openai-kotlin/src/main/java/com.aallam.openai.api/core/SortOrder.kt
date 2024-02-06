package com.aallam.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class SortOrder(val order: String) {
    companion object {
        val Ascending: SortOrder = SortOrder("asc")
        val Descending: SortOrder = SortOrder("desc")
    }
}
