package com.aallam.openai.client

import com.aallam.openai.api.moderation.ModerationRequest
import com.aallam.openai.api.moderation.TextModeration

/**
 * Given an input text, outputs if the model classifies it as violating OpenAI's content policy.
 */
interface Moderations {

    /**
     * Classifies if text violates OpenAI's Content Policy.
     */
    suspend fun moderations(request: ModerationRequest): TextModeration
}
