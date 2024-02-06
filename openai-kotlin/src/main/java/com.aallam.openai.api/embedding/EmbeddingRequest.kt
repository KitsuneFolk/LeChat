package com.aallam.openai.api.embedding

import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create an embedding request.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/embeddings)
 */
@Serializable
class EmbeddingRequest(

    /**
     * ID of the model to use.
     */
    @SerialName("model") val model: ModelId,

    /**
     * Input text to get embeddings for, encoded as an array of token. Each input must not exceed 2048 tokens in length.
     *
     * Unless you are embedding code, we suggest replacing newlines (`\n`) in your input with a single space, as we have
     * observed inferior results when newlines are present.
     */
    @SerialName("input") val input: List<String>,

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    @SerialName("user") val user: String? = null,
)

/**
 * Create an embedding request.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/embeddings)
 */
fun embeddingRequest(block: EmbeddingRequestBuilder.() -> Unit): EmbeddingRequest =
    EmbeddingRequestBuilder().apply(block).build()

/**
 * Builder of [EmbeddingRequest] instances.
 */
@OpenAIDsl
class EmbeddingRequestBuilder {

    /**
     * ID of the model to use.
     */
    var model: ModelId? = null

    /**
     * Input text to get embeddings for, encoded as an array of token. Each input must not exceed 2048 tokens in length.
     *
     * Unless you are embedding code, we suggest replacing newlines (`\n`) in your input with a single space, as we have
     * observed inferior results when newlines are present.
     */
    var input: List<String>? = null

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    var user: String? = null

    /**
     * Create [EmbeddingRequest] instance.
     */
    fun build(): EmbeddingRequest = EmbeddingRequest(
        model = requireNotNull(model) { "model is required" },
        input = requireNotNull(input) { "input is required" },
        user = user
    )
}
