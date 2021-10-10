package eu.rajniak.chat.conversation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import eu.rajniak.chat.ui.theme.ChatDemoTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConversationUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ChatDemoTheme {
                ConversationUI(ConversationViewModel())
            }
        }
    }

    @Test
    fun sendMessage() {
        // TODO: Orchestrator is being difficult - once there is more time clear data between each test
        val text = System.currentTimeMillis().toString()
        composeTestRule.onNodeWithContentDescription("Message text field").performTextInput(text)
        composeTestRule.onNodeWithContentDescription("Send").performClick()
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
}
