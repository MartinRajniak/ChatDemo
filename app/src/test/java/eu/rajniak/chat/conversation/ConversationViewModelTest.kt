package eu.rajniak.chat.conversation

import eu.rajniak.chat.data.FakeData
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
}
