package com.aallam.openai.api.image

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.model.ModelId

/**
 * Image edit request.
 */
class ImageEdit(
    /**
     * The image to edit. Must be a valid PNG file, less than 4MB, and square.
     * If mask is not provided, image must have transparency, which will be used as the mask.
     */
    val image: FileSource,

    /**
     * An additional [image] whose fully transparent areas (e.g. where alpha is zero) indicate where [image] should be
     * edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     */
    val mask: FileSource,

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
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    val user: String? = null,

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    val model: ModelId? = null,
)

/**
 * Image edit request.
 */
@BetaOpenAI
fun imageEdit(block: ImageEditBuilder.() -> Unit): ImageEdit = ImageEditBuilder().apply(block).build()

/**
 * Builder of [ImageEdit] instances.
 */
@BetaOpenAI
@OpenAIDsl
class ImageEditBuilder {
    /**
     * The image to edit. Must be a valid PNG file, less than 4MB, and square.
     * If mask is not provided, image must have transparency, which will be used as the mask.
     */
    var image: FileSource? = null

    /**
     * An additional [image] whose fully transparent areas (e.g. where alpha is zero) indicate where [image] should be
     * edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     */
    var mask: FileSource? = null

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
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    var user: String? = null

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    var model: ModelId? = null

    /**
     * Creates the [ImageEdit] instance
     */
    fun build(): ImageEdit = ImageEdit(
        image = requireNotNull(image) { "image field is required" },
        mask = requireNotNull(mask) { "mask field is required" },
        prompt = requireNotNull(prompt) { "prompt field is required" },
        n = n,
        size = size,
        user = user,
        model = model,
    )
}
