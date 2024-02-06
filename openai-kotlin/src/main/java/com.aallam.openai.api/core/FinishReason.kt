package com.aallam.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FinishReason(val value: String) {
    companion object {
        val Stop: FinishReason = FinishReason("stop")
        val Length: FinishReason = FinishReason("length")
        val FunctionCall: FinishReason = FinishReason("function_call")
        val ToolCalls: FinishReason = FinishReason("tool_calls")
    }
}
