package com.aallam.openai.api.moderation

import com.aallam.openai.api.OpenAIDsl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to classify if text violates OpenAI's Content Policy.
 */
@Serializable
class ModerationRequest(
    /**
     * The input text to classify.
     */
    @SerialName("input") val input: List<String>,

    /**
     * Moderation model.
     * Defaults to [ModerationModel.Latest].
     */
    @SerialName("model") val model: ModerationModel? = null,
)

/**
 * Request to classify if text violates OpenAI's Content Policy.
 */
fun moderationRequest(block: ModerationRequestBuilder.() -> Unit): ModerationRequest =
    ModerationRequestBuilder().apply(block).build()

/**
 * Data class representing a ModerationRequest
 */
@OpenAIDsl
class ModerationRequestBuilder {
    /**
     * The input text to classify.
     */
    var input: List<String>? = null

    /**
     * Moderation model.
     * Defaults to [ModerationModel.Latest].
     */
    var model: ModerationModel? = null

    /**
     * Creates the [ModerationRequest] instance
     */
    fun build(): ModerationRequest = ModerationRequest(
        input = requireNotNull(input) { "input is required" },
        model = model,
    )
}
