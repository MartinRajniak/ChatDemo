package eu.rajniak.chat.conversation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.util.concurrent.TimeUnit

internal class MessageListTest {

    companion object {
        private val INITIAL_TIME_IN_MILLIS = System.currentTimeMillis()

        private fun createMessage(
            author: String = "",
            text: String = "",
            timeInMillis: Long = 0L
        ) = Message(author, text, timeInMillis)
    }

    @Test
    fun isMostRecent() {
        val message1 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS)
        val message2 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + 2)
        val message3 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + 4)
        val messageList = MessageList(
            listOf(
                message2,
                message1,
                message3
            )
        )

        assertThat(messageList.isMostRecent(message1)).isFalse
        assertThat(messageList.isMostRecent(message2)).isFalse
        assertThat(messageList.isMostRecent(message3)).isTrue
    }

    @Test
    fun isNextMessageFromDifferentAuthor() {
        val message1 = createMessage(author = "Martin", timeInMillis = INITIAL_TIME_IN_MILLIS)
        val message2 = createMessage(author = "Andrea", timeInMillis = INITIAL_TIME_IN_MILLIS + 2)
        val message3 = createMessage(author = "Andrea", timeInMillis = INITIAL_TIME_IN_MILLIS + 4)
        val messageList = MessageList(
            listOf(
                message2,
                message1,
                message3
            )
        )

        assertThat(messageList.isNextMessageFromDifferentAuthor(message1)).isTrue
        assertThat(messageList.isNextMessageFromDifferentAuthor(message2)).isFalse
        assertThat(messageList.isNextMessageFromDifferentAuthor(message3)).isFalse
    }

    @Test
    fun isNextMessageMoreThan20SecondsApart() {
        val message1 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS)
        val message2 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.SECONDS.toMillis(21))
        val message3 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.SECONDS.toMillis(25))
        val messageList = MessageList(
            listOf(
                message2,
                message1,
                message3
            )
        )

        assertThat(messageList.isNextMessageMoreThan20SecondsApart(message1)).isTrue
        assertThat(messageList.isNextMessageMoreThan20SecondsApart(message2)).isFalse
        assertThat(messageList.isNextMessageMoreThan20SecondsApart(message3)).isFalse
    }

    @Test
    fun isPreviousMessageMoreThanAnHourApart() {
        val message1 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS)
        val message2 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.SECONDS.toMillis(21))
        val message3 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.HOURS.toMillis(2))
        val messageList = MessageList(
            listOf(
                message2,
                message1,
                message3
            )
        )

        assertThat(messageList.isPreviousMessageMoreThanAnHourApart(message1)).isFalse
        assertThat(messageList.isPreviousMessageMoreThanAnHourApart(message2)).isFalse
        assertThat(messageList.isPreviousMessageMoreThanAnHourApart(message3)).isTrue
    }

    @Test
    fun isOldest() {
        val message1 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS)
        val message2 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.SECONDS.toMillis(21))
        val message3 = createMessage(timeInMillis = INITIAL_TIME_IN_MILLIS + TimeUnit.HOURS.toMillis(2))
        val messageList = MessageList(
            listOf(
                message2,
                message1,
                message3
            )
        )

        assertThat(messageList.isOldest(message1)).isTrue
        assertThat(messageList.isOldest(message2)).isFalse
        assertThat(messageList.isOldest(message3)).isFalse
    }
}
