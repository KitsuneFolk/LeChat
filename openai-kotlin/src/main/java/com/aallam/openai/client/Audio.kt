package com.aallam.openai.client

import com.aallam.openai.api.audio.*

/**
 * Learn how to turn audio into text.
 */
interface Audio {

    /**
     * Transcribes audio into the input language.
     */
    suspend fun transcription(request: TranscriptionRequest): Transcription

    /**
     * Translates audio into English.
     */
    suspend fun translation(request: TranslationRequest): Translation

    /**
     * Generates audio from the input text.
     */
    suspend fun speech(request: SpeechRequest): ByteArray
}
