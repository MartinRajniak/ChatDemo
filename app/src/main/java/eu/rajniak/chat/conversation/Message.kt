package eu.rajniak.chat.conversation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages"
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val author: String,
    val text: String,
    val timeInMillis: Long
)
