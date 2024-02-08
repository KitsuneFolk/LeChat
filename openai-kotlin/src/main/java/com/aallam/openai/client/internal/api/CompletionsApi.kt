package com.aallam.openai.client.internal.api

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.client.Completions
import com.aallam.openai.client.internal.extension.streamEventsFrom
import com.aallam.openai.client.internal.extension.toStreamRequest
import com.aallam.openai.client.internal.http.HttpRequester
import com.aallam.openai.client.internal.http.perform
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementation of [Completions].
 */
internal class CompletionsApi(private val requester: HttpRequester) : Completions {

    @Deprecated("completions is deprecated, use chat completion instead")
    override suspend fun completion(request: CompletionRequest): TextCompletion {
        return requester.perform {
            it.post {
                url(path = ApiPath.Completions)
                setBody(request)
                contentType(ContentType.Application.Json)
            }.body()
        }
    }

    @Deprecated("completions is deprecated, use chat completion instead")
    override fun completions(request: CompletionRequest): Flow<TextCompletion> {
        val builder = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url(path = ApiPath.Completions)
            setBody(request.toStreamRequest())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
        return flow {
            requester.perform(builder) { response -> streamEventsFrom(response) }
        }
    }
}
