package com.aallam.openai.api.finetune

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FineTuneId(val id: String)
