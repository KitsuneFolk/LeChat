package com.aallam.openai.api.image

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.model.ModelId

/**
 * Image variant request.
 */
class ImageVariation(
    /**
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    val image: FileSource,

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    val n: Int? = null,

    /**
     * The size of the generated images.
     */
    val size: ImageSize? = null,

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    val user: String? = null,

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    val model: ModelId? = null,
)

/**
 * Image variant request.
 */
@BetaOpenAI
fun imageVariation(block: ImageVariationBuilder.() -> Unit): ImageVariation =
    ImageVariationBuilder().apply(block).build()

/**
 * Builder of [ImageVariation] instances.
 */
@BetaOpenAI
@OpenAIDsl
class ImageVariationBuilder {
    /**
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    var image: FileSource? = null

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    var n: Int? = null

    /**
     * The size of the generated images.
     */
    var size: ImageSize? = null

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    var user: String? = null

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    var model: ModelId? = null

    /**
     * Creates the [ImageVariation] instance
     */
    fun build(): ImageVariation = ImageVariation(
        image = requireNotNull(image) { "image is required" },
        n = n,
        size = size,
        user = user,
        model = model,
    )
}
