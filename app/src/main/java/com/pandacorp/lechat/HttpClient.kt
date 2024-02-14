package com.pandacorp.lechat

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

// Configuration needed to run openai-kotlin
val client = HttpClient(OkHttp) {
    engine {
        config {
            followRedirects(true)
        }
    }
}