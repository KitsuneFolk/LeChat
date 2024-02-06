package com.aallam.openai.api.image

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.model.ModelId

/**
 * Image generation request.
 */
class ImageCreation(
    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    val prompt: String,
    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    val n: Int? = null,
    /**
     * The size of the generated images.
     */
    val size: ImageSize? = null,

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    val user: String? = null,

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    val model: ModelId? = null,
)

/**
 * Image generation request.
 */
@BetaOpenAI
fun imageCreation(block: ImageCreationBuilder.() -> Unit): ImageCreation =
    ImageCreationBuilder().apply(block).build()

/**
 * Builder of [ImageCreation] instances.
 */
@BetaOpenAI
@OpenAIDsl
class ImageCreationBuilder {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    var prompt: String? = null

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    var n: Int? = null

    /**
     * The size of the generated images.
     */
    var size: ImageSize? = null

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    var user: String? = null

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    var model: ModelId? = null

    /**
     * Creates the [ImageCreation] instance
     */
    fun build(): ImageCreation = ImageCreation(
        prompt = requireNotNull(prompt),
        n = n,
        size = size,
        user = user,
        model = model,
    )
}
