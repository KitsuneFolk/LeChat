package com.aallam.openai.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generated chat message.
 */
@Serializable
data class ChatDelta(
    /**
     * The role of the author of this message.
     */
    @SerialName("role") val role: ChatRole? = null,

    /**
     * The contents of the message.
     */
    @SerialName("content") val content: String? = null,
)
