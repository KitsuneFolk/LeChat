package com.aallam.openai.api.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Model identifier.
 */
@Serializable
@JvmInline
value class ModelId(val id: String)
