package com.aallam.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The role of the author of a message.
 */
@JvmInline
@Serializable
value class Role(val role: String) {
    companion object {
        val System: Role = Role("system")
        val User: Role = Role("user")
        val Assistant: Role = Role("assistant")
        val Function: Role = Role("function")
        val Tool: Role = Role("tool")
    }
}
