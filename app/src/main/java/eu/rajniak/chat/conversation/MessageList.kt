package eu.rajniak.chat.conversation

import java.util.concurrent.TimeUnit

class MessageList
constructor(
    originalList: List<Message>
): List<Message> by ArrayList(originalList.sortedByDescending { message -> message.timeInMillis }) {

    fun isMostRecent(message: Message) = indexOf(message) == 0

    fun isNextMessageFromDifferentAuthor(message: Message): Boolean {
        val index = indexOf(message)
        if (index == 0) {
            return false
        }
        return get(index - 1).author != message.author
    }

    fun isNextMessageMoreThan20SecondsApart(message: Message): Boolean {
        val index = indexOf(message)
        if (index == 0) {
            return false
        }
        return get(index - 1).timeInMillis - message.timeInMillis > TimeUnit.SECONDS.toMillis(20)
    }
}
