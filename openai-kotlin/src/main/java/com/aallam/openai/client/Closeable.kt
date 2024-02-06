package com.aallam.openai.client

/**
 * Defines a closeable resource.
 * This will be replaced by [AutoCloseable] once it becomes stable.
 */
interface Closeable {

    /**
     * Closes underlying resources
     */
    fun close()
}
