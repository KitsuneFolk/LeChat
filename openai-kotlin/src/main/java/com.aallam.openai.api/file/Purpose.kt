package com.aallam.openai.api.file

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * File purpose.
 */
@Serializable
@JvmInline
value class Purpose(val raw: String)
