package com.aallam.openai.api.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error response from the TogetherAI API.
 *
 * @param message information about the error that occurred.
 */
@Serializable
data class TogetherAIError(
    @SerialName("error") val message: String?,
)