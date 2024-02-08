package com.aallam.openai.api.chat

import com.aallam.openai.api.chat.internal.ContentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The messages to generate chat completions for.
 */
@Serializable
data class ChatMessage(
    /**
     * The role of the author of this message.
     */
    @SerialName("role") val role: ChatRole,

    /**
     * The contents of the message.
     * **This is required for requests, and optional for responses**.
     */
    @SerialName("content") val messageContent: Content? = null,

    /**
     * The author's name of this message.
     * It May contain a-z, A-Z, 0-9, and underscores, with a maximum length of 64 characters.
     */
    @SerialName("name") val name: String? = null,
) {

    constructor(
        role: ChatRole,
        content: String? = null,
        name: String? = null
    ) : this(
        role = role,
        messageContent = content?.let { TextContent(it) },
        name = name
    )

    val content: String?
        get() = when (messageContent) {
            is TextContent? -> messageContent?.content
            else -> error("Content is not text")
        }
}

/**
 * The contents of the chat message.
 */
@Serializable(with = ContentSerializer::class)
sealed interface Content

/**
 * The chat message content as text.
 */
@JvmInline
@Serializable
value class TextContent(val content: String) : Content

/**
 *  The chat message content as a list of content parts.
 */
@JvmInline
@Serializable
value class ListContent(val content: List<ContentPart>) : Content

/**
 * Represents a chat message part.
 */
@Serializable
sealed interface ContentPart
