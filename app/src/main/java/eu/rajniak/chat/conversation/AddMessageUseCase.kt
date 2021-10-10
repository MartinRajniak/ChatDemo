package eu.rajniak.chat.conversation

import eu.rajniak.chat.ChatServiceLocator
import eu.rajniak.chat.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class AddMessageUseCase(
    private val messageStore: MessageStore = ChatServiceLocator.messageStore,
    ioDispatcher: CoroutineDispatcher = ChatServiceLocator.ioDispatcher
): UseCase<Message>(ioDispatcher) {

    override suspend fun execute(message: Message) {
        messageStore.addMessage(message)
    }
}
