package com.pandacorp.lechat.domain.model

data class MessageItem(
    var id: Long = 0,
    var role: Int,
    var message: String
) {
    companion object Role {
        const val SYSTEM = 0
        const val USER = 1
        const val AI = 2
    }
}