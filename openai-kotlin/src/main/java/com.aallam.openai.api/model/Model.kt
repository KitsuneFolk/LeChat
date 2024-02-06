package com.aallam.openai.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * OpenAI's Model.
 */
@Serializable
data class Model(
    @SerialName("id") val id: ModelId,
    @SerialName("created") val created: Long,
    @SerialName("owned_by") val ownedBy: String,
    @SerialName("permission") val permission: List<ModelPermission>? = null,
)
