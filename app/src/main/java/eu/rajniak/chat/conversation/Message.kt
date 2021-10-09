package eu.rajniak.chat.conversation

data class Message(
    val author: String,
    val text: String,
    val timeInMillis: Long
)
