package eu.rajniak.chat.data

import eu.rajniak.chat.conversation.Message
import java.util.concurrent.TimeUnit

object FakeData {

    const val CURRENT_AUTHOR = "Martin"
    const val OTHER_AUTHOR = "Sarah"

    private val quotes = listOf(
        """Fear is the mind-killer. Fear is the little-death that brings total obliteration.
            |I will face my fear.
            |I will permit it to pass over me and through me.""".trimMargin(),
        "A beginning is a very delicate time.",
        """A beginning is the time for taking the most delicate care that the balances are correct.
            |This every sister of the Bene Gesserit knows.""".trimMargin(),
        "It is by will alone I set my mind in motion.",
        """Deep in the human unconscious is a pervasive need for a logical universe that makes sense.
            |But the real universe is always one step beyond logic.""".trimMargin(),
        """The spice extends life.
            |The spice expands consciousness.
            |The spice is vital to space travel.""".trimMargin()
    )

    private val initialTime = System.currentTimeMillis()

    val messages = listOf(
        Message(
            author = CURRENT_AUTHOR,
            text = quotes[2],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(2)
        ),
        Message(
            author = OTHER_AUTHOR,
            text = quotes[1],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(10)
        ),
        Message(
            author = CURRENT_AUTHOR,
            text = quotes[0],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(30)
        ),
        Message(
            author = CURRENT_AUTHOR,
            text = quotes[2],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(32)
        ),
        Message(
            author = OTHER_AUTHOR,
            text = quotes[1],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(40)
        ),
        Message(
            author = OTHER_AUTHOR,
            text = quotes[0],
            timeInMillis = initialTime - TimeUnit.SECONDS.toMillis(60)
        )
    )
}
