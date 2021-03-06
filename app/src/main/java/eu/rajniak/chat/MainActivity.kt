package eu.rajniak.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import eu.rajniak.chat.conversation.ConversationUI
import eu.rajniak.chat.conversation.ConversationViewModel
import eu.rajniak.chat.ui.theme.ChatDemoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Update the system bars to be translucent
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }
            ChatDemoTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    ConversationUI(viewModel)
                }
            }
        }
    }
}
