package com.aallam.openai.api.audio

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class SpeechResponseFormat(val value: String) {
    companion object {
        val Mp3: SpeechResponseFormat = SpeechResponseFormat("mp3")
        val Opus: SpeechResponseFormat = SpeechResponseFormat("opus")
        val Aac: SpeechResponseFormat = SpeechResponseFormat("aac")
        val Flac: SpeechResponseFormat = SpeechResponseFormat("flac")
    }
}
