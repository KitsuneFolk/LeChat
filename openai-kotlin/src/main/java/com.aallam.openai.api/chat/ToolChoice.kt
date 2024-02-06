package com.aallam.openai.api.chat

import com.aallam.openai.api.chat.internal.ToolChoiceSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Controls which (if any) function is called by the model.
 */
@Serializable(with = ToolChoiceSerializer::class)
sealed interface ToolChoice {

    /**
     * Represents a function call mode.
     * - `"none"` means the model will not call a function and instead generates a message.
     * - `"auto"` means the model can pick between generating a message or calling a function.
     */
    @JvmInline
    @Serializable
    value class Mode(val value: String) : ToolChoice

    /**
     * Specifies a tool the model should use.
     */
    @Serializable
    data class Named(
        @SerialName("type") val type: ToolType? = null,
        @SerialName("function") val function: FunctionToolChoice? = null,
    ) : ToolChoice

    companion object {
        /** Represents the `auto` mode. */
        val Auto: ToolChoice = Mode("auto")

        /** Represents the `none` mode. */
        val None: ToolChoice = Mode("none")

        /** Specifies a function for the model to call **/
        fun function(name: String): ToolChoice =
            Named(type = ToolType.Function, function = FunctionToolChoice(name = name))
    }
}

/**
 * Represents the function to call by the model.
 */
@Serializable
data class FunctionToolChoice(val name: String)
