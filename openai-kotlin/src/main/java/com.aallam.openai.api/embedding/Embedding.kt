package com.aallam.openai.api.embedding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An embedding result.
 * [documentation](https://beta.openai.com/docs/api-reference/embeddings)
 */
@Serializable
class Embedding(
    @SerialName("embedding") val embedding: List<Double>,
    @SerialName("index") val index: Int,
)
