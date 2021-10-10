package eu.rajniak.chat

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.rajniak.chat.conversation.Message
import eu.rajniak.chat.conversation.MessagesDao

@Database(entities = [Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messagesDao(): MessagesDao
}
