package com.aallam.openai.api.chat

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.core.Usage
import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An object containing a response from the chat completion api.
 *
 * [documentation](https://platform.openai.com/docs/api-reference/chat/create)
 */
@Serializable
data class ChatCompletion(
    /**
     * A unique id assigned to this completion
     */
    @SerialName("id") val id: String,
    /**
     * The creation time in epoch milliseconds.
     */
    @SerialName("created") val created: Long,

    /**
     * The model used.
     */
    @SerialName("model") val model: ModelId,

    /**
     * A list of generated completions
     */
    @SerialName("choices") val choices: List<ChatChoice>,

    /**
     * Text completion usage data.
     */
    @SerialName("usage") val usage: Usage? = null,

    /**
     * This fingerprint represents the backend configuration that the model runs with.
     *
     * Can be used in conjunction with the seed request parameter to understand when backend changes have been made that
     * might impact determinism.
     */
    @SerialName("system_fingerprint") val systemFingerprint: String? = null,
)
