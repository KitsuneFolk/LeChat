package com.aallam.openai.api.image

import com.aallam.openai.api.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The size of the generated images.
 */
@JvmInline
@Serializable
value class ImageSize(val size: String) {

    companion object {

        /**
         * Size image of dimension 256x256.
         */
        val is256x256: ImageSize = ImageSize("256x256")

        /**
         * Size image of dimension 512x512.
         */
        val is512x512: ImageSize = ImageSize("512x512")

        /**
         * Size image of dimension 1024x1024.
         */
        val is1024x1024: ImageSize = ImageSize("1024x1024")
    }
}
