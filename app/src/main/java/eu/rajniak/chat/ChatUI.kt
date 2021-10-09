package eu.rajniak.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import eu.rajniak.chat.ui.theme.ChatDemoTheme

@Composable
fun ChatUI() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(Modifier.fillMaxSize()) {
            Toolbar(
                modifier = Modifier.statusBarsPadding()
            )
            MessageList(
                modifier = Modifier.weight(1f)
            )
            TextEntryBox(
                modifier = Modifier.navigationBarsWithImePadding()
            )
        }
    }
}

@Composable
fun Toolbar(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Text("Toolbar")
    }
}

@Composable
fun MessageList(
    modifier: Modifier
) {
    Box(modifier = modifier){
        Text("MessageList")
    }
}

@Composable
fun TextEntryBox(
    modifier: Modifier
) {
    Box(modifier = modifier){
        Text("TextEntryBox")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChatDemoTheme {
        ChatUI()
    }
}
