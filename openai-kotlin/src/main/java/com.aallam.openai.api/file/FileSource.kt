package com.aallam.openai.api.file

import com.aallam.openai.api.OpenAIDsl
import okio.FileSystem
import okio.Path
import okio.Source

/**
 * Represents a file resource.
 */
class FileSource(
    /**
     * File name.
     */
    val name: String,

    /**
     * File source.
     */
    val source: Source,
) {

    /**
     * Create [FileSource] instance.
     *
     * @param path file path to upload
     * @param fileSystem file system to be used
     */
    constructor(path: Path, fileSystem: FileSystem) : this(path.name, fileSystem.source(path))
}

/**
 * Represents a file resource.
 */
fun fileSource(block: FileSourceBuilder.() -> Unit): FileSource = FileSourceBuilder().apply(block).build()

/**
 * Builder of [FileSource] instances.
 */
@OpenAIDsl
class FileSourceBuilder {

    /**
     * File name.
     */
    var name: String? = null

    /**
     * File source.
     */
    var source: Source? = null

    /**
     * Creates the [FileSource] instance
     */
    fun build(): FileSource = FileSource(
        name = requireNotNull(name) { "name is required" },
        source = requireNotNull(source) { "source is required" }
    )
}
