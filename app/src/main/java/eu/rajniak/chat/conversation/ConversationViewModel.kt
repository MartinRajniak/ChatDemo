package eu.rajniak.chat.conversation

import androidx.lifecycle.ViewModel
import eu.rajniak.chat.data.FakeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConversationViewModel: ViewModel() {

    private val _messages = MutableStateFlow(FakeData.messages)
    val messages: StateFlow<MessageList> = _messages

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
        val originalList = _messages.value
        val newList = MessageList(originalList.plus(message))
        _messages.value = newList
    }
}
