package com.pandacorp.lechat

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

// Configuration needed to run openai-kotlin
val client = HttpClient(OkHttp) {
    engine {
        config {
            followRedirects(true)
        }

    }
}