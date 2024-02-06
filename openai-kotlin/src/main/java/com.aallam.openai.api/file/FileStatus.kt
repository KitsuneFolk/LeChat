package com.aallam.openai.api.file

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * File status.
 */
@Serializable
@JvmInline
value class FileStatus(val raw: String)
