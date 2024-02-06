package com.aallam.openai.api.audio

import com.aallam.openai.api.OpenAIDsl
import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generates audio from the input text.
 */
@Serializable
data class SpeechRequest(

    /**
     * One of the available TTS models: tts-1 or tts-1-hd
     */
    @SerialName("model") val model: ModelId,

    /**
     * The text to generate audio for. The maximum length is 4096 characters.
     */
    @SerialName("input") val input: String,

    /**
     * The voice to use when generating the audio
     */
    @SerialName("voice") val voice: Voice? = null,

    /**
     * The format to audio in.
     */
    @SerialName("response_format") val responseFormat: SpeechResponseFormat? = null,

    /**
     * The speed of the generated audio. Select a value from 0.25 to 4.0. 1.0 is the default.
     */
    @SerialName("speed") val speed: Double? = null,
)

/**
 * Creates a new [SpeechRequest] instance.
 */
fun speechRequest(block: SpeechRequestBuilder.() -> Unit): SpeechRequest =
    SpeechRequestBuilder().apply(block).build()

/**
 * A speech request builder.
 */
@OpenAIDsl
class SpeechRequestBuilder {

    /**
     * One of the available TTS models: tts-1 or tts-1-hd
     */
    var model: ModelId? = null

    /**
     * The text to generate audio for. The maximum length is 4096 characters.
     */
    var input: String? = null

    /**
     * The voice to use when generating the audio
     */
    var voice: Voice? = null

    /**
     * The format to audio in.
     */
    var responseFormat: SpeechResponseFormat? = null

    /**
     * The speed of the generated audio. Select a value from 0.25 to 4.0. 1.0 is the default.
     */
    var speed: Double? = null

    /**
     * Builds and returns a [SpeechRequest] instance.
     */
    fun build(): SpeechRequest = SpeechRequest(
        model = requireNotNull(model) { "model is required" },
        input = requireNotNull(input) { "input is required" },
        voice = voice,
        responseFormat = responseFormat,
        speed = speed
    )
}
