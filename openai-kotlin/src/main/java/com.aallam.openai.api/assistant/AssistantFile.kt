package com.aallam.openai.api.assistant

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.file.FileId
import kotlinx.serialization.SerialName

/**
 * File attached to an assistant.
 */
@BetaOpenAI
data class AssistantFile(
    /**
     * The identifier, which can be referenced in API endpoints.
     */
    @SerialName("id") val id: FileId,

    /**
     * The Unix timestamp (in seconds) for when the assistant file was created.
     */
    @SerialName("created_at") val createdAt: Int,

    /**
     * The assistant ID that the file is attached to.
     */
    @SerialName("assistant_id") val assistantId: AssistantId
)
