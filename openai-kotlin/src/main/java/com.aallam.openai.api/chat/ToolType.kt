package com.aallam.openai.api.chat

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The type of the tool.
 */
@JvmInline
@Serializable
value class ToolType(val value: String) {
    companion object {
        /**
         * Represents 'function' tool.
         */
        val Function: ToolType = ToolType("function")
    }
}
