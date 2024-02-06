package com.aallam.openai.api.run

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.chat.ToolId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a tool output.
 */
@BetaOpenAI
@Serializable
data class ToolOutput(
    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     */
    @SerialName("tool_call_id") val toolCallId: ToolId? = null,
    /**
     * The output of the tool call to be submitted to continue the run.
     */
    @SerialName("output") val output: String? = null,
)

/**
 * Creates a [ToolOutput] instance using the provided builder block.
 */
@BetaOpenAI
fun toolOutput(block: ToolOutputBuilder.() -> Unit): ToolOutput = ToolOutputBuilder().apply(block).build()

/**
 * A tool output builder.
 */
@BetaOpenAI
@OpenAIDsl
class ToolOutputBuilder {
    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     */
    var toolCallId: ToolId? = null

    /**
     * The output of the tool call to be submitted to continue the run.
     */
    var output: String? = null

    /**
     * Builds and returns a [ToolOutput] instance.
     */
    fun build(): ToolOutput = ToolOutput(
        toolCallId = toolCallId,
        output = output
    )
}
