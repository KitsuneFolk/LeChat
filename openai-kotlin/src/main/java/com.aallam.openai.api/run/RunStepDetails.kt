package com.aallam.openai.api.run

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.file.FileId
import com.aallam.openai.api.message.MessageId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A run step object.
 */
@BetaOpenAI
@Serializable
sealed interface RunStepDetails

/**
 * Details of the message creation by the run step.
 */
@BetaOpenAI
@Serializable
@SerialName("message_creation")
data class MessageCreationStepDetails(
    /**
     * The message creation details.
     */
    @SerialName("message_creation") val messageCreation: MessageCreation,
) : RunStepDetails

/**
 * Details of the message that occurred during the run step.
 */
@Serializable
@BetaOpenAI
data class MessageCreation(
    /**
     * The ID of the message that was created by this run step.
     */
    @SerialName("message_id") val messageId: MessageId,
)

/**
 * Details of the tool call.
 */
@BetaOpenAI
@Serializable
@SerialName("tool_calls")
data class ToolCallStepDetails(
    /**
     * An array of tool calls the run step was involved in.
     * These can be associated with one of three types of tools:
     * [ToolCallStep.CodeInterpreter], [ToolCallStep.RetrievalTool], or [ToolCallStep.FunctionTool].
     */
    @SerialName("tool_calls") val toolCalls: List<ToolCallStep>? = null,
) : RunStepDetails

@BetaOpenAI
@Serializable
sealed interface ToolCallStep {

    @BetaOpenAI
    @Serializable
    @SerialName("code_interpreter")
    data class CodeInterpreter(
        /**
         * The ID of the tool call.
         */
        @SerialName("id") val id: ToolCallStepId,
        /**
         * The Code Interpreter tool call definition.
         */
        @SerialName("code_interpreter") val codeInterpreter: CodeInterpreterToolCall,
    ) : ToolCallStep

    @BetaOpenAI
    @Serializable
    @SerialName("retrieval")
    data class RetrievalTool(
        /**
         * The ID of the tool call object.
         */
        @SerialName("id") val id: ToolCallStepId,
        /**
         * For now, this is always going to be an empty object.
         */
        @SerialName("retrieval") val retrieval: Map<String, String>,
    ) : ToolCallStep

    @BetaOpenAI
    @Serializable
    @SerialName("function")
    data class FunctionTool(
        /**
         * The ID of the tool call object.
         */
        @SerialName("id") val id: ToolCallStepId,
        /**
         * The definition of the function that was called.
         */
        @SerialName("function") val function: FunctionToolCallStep,
    ) : ToolCallStep
}

@BetaOpenAI
@Serializable
data class FunctionToolCallStep(
    /**
     * The name of the function.
     */
    @SerialName("name") val name: String,

    /**
     * The arguments passed to the function.
     */
    @SerialName("arguments") val arguments: String,

    /**
     * The output of the function. This will be null if the outputs have not been submitted yet.
     */
    @SerialName("output") val output: String? = null,
)

@BetaOpenAI
@Serializable
data class CodeInterpreterToolCall(
    /**
     * The input to the Code Interpreter tool call.
     */
    val input: String,

    /**
     * The outputs from the Code Interpreter tool call. Code Interpreter can output one or more items, including a text
     * (logs) or images (image). Each of these is represented by a different object type.
     */
    val outputs: List<CodeInterpreterToolCallOutput>
)

@BetaOpenAI
@Serializable
sealed interface CodeInterpreterToolCallOutput {
    /**
     * Code interpreter log output.
     *
     * Text output from the Code Interpreter tool call as part of a run step.
     */
    @BetaOpenAI
    @Serializable
    @SerialName("logs")
    data class Logs(
        /**
         * The text output from the Code Interpreter tool call.
         */
        @SerialName("text") val text: String? = null,
    ) : CodeInterpreterToolCallOutput

    /**
     * Code interpreter image output
     */
    @BetaOpenAI
    @Serializable
    @SerialName("image")
    data class Image(
        /**
         * The image output from the Code Interpreter tool call.
         */
        @SerialName("image") val image: CodeInterpreterImage,
    ) : CodeInterpreterToolCallOutput

}

/**
 * Code interpreter image
 */
@BetaOpenAI
@Serializable
data class CodeInterpreterImage(
    /**
     * The file ID of the image.
     */
    @SerialName("file_id") val fileId: FileId,
)
