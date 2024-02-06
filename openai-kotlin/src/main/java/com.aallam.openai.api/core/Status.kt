package com.aallam.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Operation status.
 */
@Serializable
@JvmInline
value class Status(val value: String) {
    companion object {
        val Succeeded: Status = Status("succeeded")
        val Processed: Status = Status("processed")
        val Deleted: Status = Status("deleted")
        val Failed: Status = Status("failed")
        val Cancelled: Status = Status("cancelled")
        val ValidatingFiles: Status = Status("validating_files")
        val Queued: Status = Status("queued")
        val Running: Status = Status("running")
        val InProgress: Status = Status("in_progress")
        val RequiresAction: Status = Status("requires_action")
        val Cancelling: Status = Status("cancelling")
        val Completed: Status = Status("completed")
        val Expired: Status = Status("expired")
    }
}
