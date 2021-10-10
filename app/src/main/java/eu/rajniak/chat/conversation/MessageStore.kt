package eu.rajniak.chat.conversation

import kotlinx.coroutines.flow.Flow

class MessageStore(
    private val messagesDao: MessagesDao
) {
    fun messages(): Flow<List<Message>> = messagesDao.loadAll()

    suspend fun addMessage(message: Message) = messagesDao.save(message)
}
