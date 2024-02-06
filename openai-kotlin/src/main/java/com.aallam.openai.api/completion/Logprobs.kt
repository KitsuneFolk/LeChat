package com.aallam.openai.api.completion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Log probabilities of different token options?
 * Returned if [CompletionRequest.logprobs] is greater than zero.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/create-completion)
 */
@Serializable
data class Logprobs(
    /**
     * The tokens chosen by the completion api
     */
    @SerialName("tokens") val tokens: List<String>,

    /**
     * The log probability of each token in [tokens]
     */
    @SerialName("token_logprobs") val tokenLogprobs: List<Double>,

    /**
     * A map for each index in the completion result.
     * The map contains the top [CompletionRequest.logprobs] tokens and their probabilities
     */
    @SerialName("top_logprobs") val topLogprobs: List<Map<String, Double>>,

    /**
     * The character offset from the start of the returned text for each of the chosen tokens.
     */
    @SerialName("text_offset") val textOffset: List<Int>,
)
