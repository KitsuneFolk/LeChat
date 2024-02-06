package com.aallam.openai.api.file

import com.aallam.openai.api.OpenAIDsl

/**
 * Request to upload a file.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/files/upload)
 */
class FileUpload(

    /**
     * The [JSON Lines](https://jsonlines.readthedocs.io/en/latest/) file to be uploaded.
     *
     * If the [purpose] is set to "fine-tune", each line is a JSON record with "prompt" and "completion" fields
     * representing your [training examples](https://beta.openai.com/docs/guides/fine-tuning/prepare-training-data).
     */
    val file: FileSource,

    /**
     * The intended purpose of the uploaded documents.
     *
     * Use "fine-tune" for [Fine-tuning](https://beta.openai.com/docs/api-reference/fine-tunes).
     * This allows us to validate the format of the uploaded file.
     */
    val purpose: Purpose,
)

/**
 * Request to upload a file.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/files/upload)
 */
fun fileUpload(block: FileUploadBuilder.() -> Unit): FileUpload = FileUploadBuilder().apply(block).build()

/**
 * Builders of [FileUpload] instances.
 */
@OpenAIDsl
class FileUploadBuilder {

    /**
     * The [JSON Lines](https://jsonlines.readthedocs.io/en/latest/) file to be uploaded.
     *
     * If the [purpose] is set to "fine-tune", each line is a JSON record with "prompt" and "completion" fields
     * representing your [training examples](https://beta.openai.com/docs/guides/fine-tuning/prepare-training-data).
     */
    var file: FileSource? = null

    /**
     * The intended purpose of the uploaded documents.
     *
     * Use "fine-tune" for [Fine-tuning](https://beta.openai.com/docs/api-reference/fine-tunes).
     * This allows us to validate the format of the uploaded file.
     */
    var purpose: Purpose? = null

    /**
     * Create a new instance of [FileUpload].
     */
    fun build(): FileUpload = FileUpload(
        file = requireNotNull(file) { "file is required" },
        purpose = requireNotNull(purpose) { "purpose is required" },
    )
}
