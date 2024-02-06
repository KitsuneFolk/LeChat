package com.aallam.openai.api.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Delete operation response.
 */
@Serializable
class DeleteResponse(
    @SerialName("id") val id: String,
    @SerialName("object") val objectType: String,
    @SerialName("deleted") val deleted: Boolean
)
