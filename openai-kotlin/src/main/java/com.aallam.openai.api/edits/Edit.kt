package com.aallam.openai.api.edits

import com.aallam.openai.api.completion.Choice
import com.aallam.openai.api.core.Usage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response to the edit creation request.
 */
@Serializable
class Edit(
    /**
     * The creation time in epoch milliseconds.
     */
    @SerialName("created") val created: Long,

    /**
     * A list of generated completions.
     */
    @SerialName("choices") val choices: List<Choice>,

    /**
     * Edit usage data.
     */
    @SerialName("usage") val usage: Usage,
)
