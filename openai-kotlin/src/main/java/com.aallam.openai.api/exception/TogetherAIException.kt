package com.aallam.openai.api.exception

class TogetherAIException(
    message: String? = null,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)