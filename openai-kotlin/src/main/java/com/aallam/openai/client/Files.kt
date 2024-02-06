package com.aallam.openai.client

import com.aallam.openai.api.file.File
import com.aallam.openai.api.file.FileUpload
import com.aallam.openai.api.file.FileId

/**
 * Files are used to upload documents that can be used across features like [Answers], [Searches], and [Classifications]
 */
interface Files {

    /**
     * Upload a file that contains document(s) to be used across various endpoints/features.
     * Currently, the size of all the files uploaded by one organization can be up to 1 GB.
     */
    suspend fun file(request: FileUpload): File

    /**
     * Returns a list of files that belong to the user's organization.
     */
    suspend fun files(): List<File>

    /**
     * Returns information about a specific file.
     */
    suspend fun file(fileId: FileId): File?

    /**
     * Delete a file. Only owners of organizations can delete files currently.
     */
    suspend fun delete(fileId: FileId): Boolean

    /**
     * Returns the contents of the specified [fileId].
     */
    suspend fun download(fileId: FileId): ByteArray
}
