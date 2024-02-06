package com.aallam.openai.api.chat

import com.aallam.openai.api.core.Usage
import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An object containing a response from the chat stream completion api.
 *
 * [documentation](https://platform.openai.com/docs/api-reference/chat/create)
 */
@Serializable
data class ChatCompletionChunk(
    /**
     * A unique id assigned to this completion
     */
    @SerialName("id")
    val id: String,

    /**
     * The creation time in epoch milliseconds.
     */
    @SerialName("created")
    val created: Int,

    /**
     * The model used.
     */
    @SerialName("model")
    val model: ModelId? = null,

    /**
     * A list of generated completions
     */
    @SerialName("choices")
    val choices: List<ChatChunk>,

    /**
     * Text completion usage data.
     */
    @SerialName("usage")
    val usage: Usage? = null,
)
