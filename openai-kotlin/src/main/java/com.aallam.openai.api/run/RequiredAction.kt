package com.aallam.openai.api.run

import com.aallam.openai.api.BetaOpenAI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details on the action required to continue the run.
 */
@BetaOpenAI
@Serializable
sealed interface RequiredAction {

    @Serializable
    @SerialName("submit_tool_outputs")
    class SubmitToolOutputs(
        /**
         * A list of the relevant tool calls.
         */
        @SerialName("submit_tool_outputs") val toolOutputs: ToolOutputs,
    ) : RequiredAction
}
