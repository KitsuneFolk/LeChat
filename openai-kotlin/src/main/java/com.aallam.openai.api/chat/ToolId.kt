package com.aallam.openai.api.chat

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The ID of the tool call.
 */
@JvmInline
@Serializable
value class ToolId(val id: String)
