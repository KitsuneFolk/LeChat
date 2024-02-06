package com.aallam.openai.api.audio

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The voice to use when generating the audio
 */
@Serializable
@JvmInline
value class Voice(val value: String) {
    companion object {
        val Alloy: Voice = Voice("alloy")
        val Echo: Voice = Voice("echo")
        val Fable: Voice = Voice("fable")
        val Onyx: Voice = Voice("onyx")
        val Nova: Voice = Voice("nova")
        val Shimmer: Voice = Voice("shimmer")
    }
}
