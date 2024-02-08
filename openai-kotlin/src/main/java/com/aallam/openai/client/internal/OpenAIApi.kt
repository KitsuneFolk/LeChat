package com.aallam.openai.client.internal

import com.aallam.openai.client.*
import com.aallam.openai.client.internal.api.*
import com.aallam.openai.client.internal.http.HttpRequester

/**
 * Implementation of [OpenAI].
 *
 * @param requester http transport layer
 */
internal class OpenAIApi(
    private val requester: HttpRequester
) : OpenAI,
    Completions by CompletionsApi(requester),
    Chat by ChatApi(requester),
    Closeable by requester
