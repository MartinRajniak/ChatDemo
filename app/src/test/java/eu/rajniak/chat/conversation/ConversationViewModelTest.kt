package eu.rajniak.chat.conversation

import eu.rajniak.chat.MainCoroutineExtension
import eu.rajniak.chat.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class ConversationViewModelTest {

    // Overrides Dispatchers.Main used in Coroutines
    @RegisterExtension
    @JvmField
    var coroutineExtension = MainCoroutineExtension()

    private lateinit var viewModel: ConversationViewModel

    @BeforeEach
    fun setUp() {
        initViewModel()
    }

    private fun initViewModel() {
        val messagesDao = FakeMessagesDao()
        val messageStore = MessageStore(messagesDao)
        val addMessageUseCase = AddMessageUseCase(messageStore, coroutineExtension.testDispatcher)
        viewModel = ConversationViewModel(addMessageUseCase, messageStore)
    }

    @Test
    fun initialState() = coroutineExtension.runBlockingTest {
        assertThat(viewModel.messages.first()).isEmpty()
    }

    @Test
    fun addMessage() = coroutineExtension.runBlockingTest {
        val newMessageText = "text"
        viewModel.addMessage(newMessageText)

        assertThat(viewModel.messages.first()).anyMatch { message -> message.text == newMessageText }
    }

    @Test
    fun addFakeReply() = coroutineExtension.runBlockingTest {
        val originalSize = viewModel.messages.first().size
        viewModel.addFakeReply()

        val newList = viewModel.messages.first()
        assertThat(newList).hasSize(originalSize + 1)
        assertThat(newList.last()).matches { message -> message.author == FakeData.OTHER_AUTHOR }
    }
}

class FakeMessagesDao : MessagesDao {

    private val _messages = MutableStateFlow<List<Message>>(listOf())

    override fun save(message: Message) {
        val originalList = _messages.value
        val newList = MessageList(originalList.plus(message))
        _messages.value = newList
    }

    override fun save(messages: List<Message>) {
        val originalList = _messages.value
        val newList = MessageList(originalList.plus(messages))
        _messages.value = newList
    }

    override fun loadAll() = _messages
}
