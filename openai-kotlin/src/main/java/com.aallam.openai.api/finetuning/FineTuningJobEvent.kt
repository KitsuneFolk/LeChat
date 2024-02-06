package com.aallam.openai.api.finetuning;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Data class representing a fine-tuning job event.
 */
@Serializable
data class FineTuningJobEvent(

    /**
     * The identifier of the fine-tuning job event.
     */
    @SerialName("id")
    val id: String,

    /**
     * The Unix timestamp (in seconds) for when the event was created.
     */
    @SerialName("created_at")
    val createdAt: Int,

    /**
     * The severity level of the event, which can be either "info", "warn", or "error".
     */
    @SerialName("level")
    val level: Level,

    /**
     * A human-readable message providing more details about the event.
     */
    @SerialName("message")
    val message: String
)


/**
 * Represents the severity level of a fine-tuning job event.
 */
@Serializable
@JvmInline
value class Level(val value: String) {
    companion object {
        /**
         * An informational event.
         */
        val INFO: Level = Level("info")

        /**
         * A warning event.
         */
        val WARN: Level = Level("warn")

        /**
         * An error event.
         */
        val ERROR: Level = Level("error")
    }
}
