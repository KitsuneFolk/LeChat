package com.aallam.openai.api.file

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * File identifier.
 */
@Serializable
@JvmInline
value class FileId(val id: String)
