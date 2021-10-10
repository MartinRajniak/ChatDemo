package eu.rajniak.chat

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import eu.rajniak.chat.conversation.FakeData
import eu.rajniak.chat.conversation.MessageStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    // Pre-populate database with preview messages
                    MainScope().launch(ioDispatcher) {
                        database.messagesDao().save(FakeData.previewMessages)
                    }
                }
            })
            .build()
    }
}
