package com.aallam.openai.api.moderation

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Moderation model.
 */
@JvmInline
@Serializable
value class ModerationModel(val model: String) {

    companion object {

        /**
         * Ensures you are always using the most accurate model.
         */
        val Stable: ModerationModel = ModerationModel("text-moderation-stable")

        /**
         * Advanced notice is provided before updating this model.
         */
        val Latest: ModerationModel = ModerationModel("text-moderation-latest")
    }
}
