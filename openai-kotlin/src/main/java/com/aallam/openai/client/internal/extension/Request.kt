package com.aallam.openai.client.internal.extension

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.client.internal.JsonLenient
import kotlinx.serialization.json.*

/**
 * Adds `stream` parameter to the request.
 */
internal fun CompletionRequest.toStreamRequest(): JsonElement {
    val json = JsonLenient.encodeToJsonElement(CompletionRequest.serializer(), this)
    return streamRequestOf(json)
}

internal inline fun <reified T> streamRequestOf(serializable: T): JsonElement {
    val enableStream = "stream" to JsonPrimitive(true)
    val json = JsonLenient.encodeToJsonElement(serializable)
    val map = json.jsonObject.toMutableMap().also { it += enableStream }
    return JsonObject(map)
}