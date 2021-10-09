package eu.rajniak.chat.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.rajniak.chat.R
import eu.rajniak.chat.components.BottomBar
import eu.rajniak.chat.components.TopBar
import eu.rajniak.chat.data.FakeData
import eu.rajniak.chat.data.FakeData.OTHER_AUTHOR
import eu.rajniak.chat.theme.ChatDemoTheme

@Composable
fun ConversationUI() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = { Toolbar() },
            bottomBar = {
                BottomBar {
                    TextEntryBox()
                }
            },
        ) { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                MessageListUI()
            }
        }
    }
}


@Composable
fun Toolbar() {
    TopBar(
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile picture"
            )
            Spacer(modifier = Modifier.padding(end = 4.dp))
            Text(text = OTHER_AUTHOR)
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }
    )
}

@Composable
fun MessageListUI() {
    LazyColumn(
        reverseLayout = true,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val messages = FakeData.messages
        itemsIndexed(messages) { index: Int, message: Message ->
            val showTail = messages.isMostRecent(message)
                    || messages.isNextMessageFromDifferentAuthor(message)
                    || messages.isNextMessageMoreThan20SecondsApart(message)
            MessageUI(message, showTail)
        }
    }
}

@Composable
fun TextEntryBox() {
    var value by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Send, contentDescription = "Send")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChatDemoTheme {
        ConversationUI()
    }
}
