package eu.rajniak.chat.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.rajniak.chat.ChatServiceLocator
import eu.rajniak.chat.util.WhileViewSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val addMessageUseCase: AddMessageUseCase = AddMessageUseCase(),
    messageStore: MessageStore = ChatServiceLocator.messageStore
): ViewModel() {

    val messages: StateFlow<MessageList> = messageStore
        .messages()
        .map { list -> MessageList(list) }
        .stateIn(viewModelScope, WhileViewSubscribed, MessageList(listOf()))

    fun addMessage(text: String) {
        val message = Message(
            author = FakeData.CURRENT_AUTHOR,
            text = text,
            timeInMillis = System.currentTimeMillis()
        )
        addMessage(message)
    }

    fun addFakeReply() {
        val message = Message(
            author = FakeData.OTHER_AUTHOR,
            text = FakeData.quotes.random(),
            timeInMillis = System.currentTimeMillis()
        )
        addMessage(message)
    }

    private fun addMessage(message: Message) {
        viewModelScope.launch {
            addMessageUseCase.invoke(message)
        }
    }
}
