package com.aallam.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Organization identifier.
 */
@Serializable
@JvmInline
value class OrganizationId(val id: String)
