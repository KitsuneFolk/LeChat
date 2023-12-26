package com.pandacorp.togetheraichat.domain.model

data class MessageItem(
    var id: Long = 0,
    var message: String,
    var role: Int
) {
    companion object Role {
        const val USER = 0
        const val AI = 1
    }
}