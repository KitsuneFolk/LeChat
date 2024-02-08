package com.aallam.openai.api.chat

import com.aallam.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Creates a completion for the chat message.
 */
@Serializable
class ChatCompletionRequest(
    /**
     * ID of the model to use.
     */
    @SerialName("model") val model: ModelId,

    /**
     * The messages to generate chat completions for.
     */
    @SerialName("messages") val messages: List<ChatMessage>,

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic.
     *
     * We generally recommend altering this or [topP] but not both.
     */
    @SerialName("temperature") val temperature: Double? = null,

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results
     * of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass
     * are considered.
     *
     * We generally recommend altering this or [temperature] but not both.
     */
    @SerialName("top_p") val topP: Double? = null,
)
