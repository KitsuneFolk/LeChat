package com.pandacorp.togetheraichat.domain.model

data class MessageItem(
    var id: Long = 0,
    var message: String,
    var role: Int
) {
    companion object Role {
        const val SYSTEM = 0
        const val USER = 1
        const val AI = 2
    }
}