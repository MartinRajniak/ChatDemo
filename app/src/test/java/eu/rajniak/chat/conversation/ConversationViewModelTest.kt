package eu.rajniak.chat.conversation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ConversationViewModelTest {

    private val viewModel = ConversationViewModel()

    @Test
    fun initialState() {
        assertThat(viewModel.messages.value).isEqualTo(FakeData.messages)
    }

    @Test
    fun addMessage() {
        val newMessageText = "text"
        viewModel.addMessage(newMessageText)

        assertThat(viewModel.messages.value).anyMatch { message -> message.text == newMessageText }
    }

    @Test
    fun addFakeReply() {
        val originalSize = viewModel.messages.value.size
        viewModel.addFakeReply()

        val newList = viewModel.messages.value
        assertThat(newList).hasSize(originalSize + 1)
        assertThat(newList.last()).matches { message -> message.author == FakeData.OTHER_AUTHOR }
    }
}
