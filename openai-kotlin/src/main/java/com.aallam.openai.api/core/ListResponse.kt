package com.aallam.openai.api.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response as List of [T].
 */
@Serializable
class ListResponse<T>(

    /**
     * List containing the actual results.
     */
    @SerialName("data") val data: List<T>,

    /**
     * Embedding usage data.
     */
    @SerialName("usage") val usage: Usage? = null,

    /**
     * The ID of the first element returned.
     */
    @SerialName("first_id") val firstId: String? = null,

    /**
     * The ID of the last element returned.
     */
    @SerialName("last_id") val lastId: String? = null,

    /**
     * If the list is truncated.
     */
    @SerialName("has_more") val hasMore: Boolean? = null,
) : List<T> by data
