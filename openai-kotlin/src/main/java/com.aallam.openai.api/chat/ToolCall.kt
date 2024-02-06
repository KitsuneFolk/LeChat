package com.aallam.openai.api.chat

import com.aallam.openai.api.OpenAIDsl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details of the tool call.
 */
@Serializable
sealed interface ToolCall {

    /**
     * Tool call.
     */
    @Serializable
    @SerialName("function")
    data class Function(
        /** The ID of the tool call. **/
        @SerialName("id") val id: ToolId,
        /** The function that the model called. **/
        @SerialName("function") val function: FunctionCall,
    ) : ToolCall
}

/**
 * Tool: function call.
 */
fun function(block: FunctionToolCallBuilder.() -> Unit): ToolCall.Function =
    FunctionToolCallBuilder().apply(block).build()


/**
 * Tool call builder.
 */
@OpenAIDsl
class FunctionToolCallBuilder {

    /** The ID of the tool call. **/
    var id: ToolId? = null

    /** The function that the model called. **/
    var function: FunctionCall? = null

    /**
     * Create [ToolCall] instance.
     */
    fun build(): ToolCall.Function = ToolCall.Function(
        id = requireNotNull(id) { "id is required" },
        function = requireNotNull(function) { "function is required" },
    )
}
