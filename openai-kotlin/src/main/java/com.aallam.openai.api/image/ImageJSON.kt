package com.aallam.openai.api.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generated image JSON (base 64).
 */
@Serializable
data class ImageJSON(

    /**
     * Image url string.
     */
    @SerialName("b64_json")
    val b64JSON: String,

    /**
     * The prompt that was used to generate the image if there was any revision to the prompt.
     */
    @SerialName("revised_prompt")
    val revisedPrompt: String? = null,
)
