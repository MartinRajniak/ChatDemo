package eu.rajniak.chat

import android.content.Context
import androidx.room.Room
import eu.rajniak.chat.conversation.MessageStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object ChatServiceLocator {

    lateinit var database: ChatDatabase
        private set

    val messageStore by lazy {
        MessageStore(
            messagesDao = database.messagesDao()
        )
    }

    val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    fun initialize(context: Context) {
        database = Room.databaseBuilder(context, ChatDatabase::class.java, "data.db")
            // TODO: for production handle migration
            .fallbackToDestructiveMigration()
            .build()
    }
}
