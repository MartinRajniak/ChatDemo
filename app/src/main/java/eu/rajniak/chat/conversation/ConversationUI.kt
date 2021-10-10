package eu.rajniak.chat.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.rajniak.chat.R
import eu.rajniak.chat.ui.components.BottomBar
import eu.rajniak.chat.ui.components.TopBar
import eu.rajniak.chat.conversation.FakeData.OTHER_AUTHOR
import eu.rajniak.chat.ui.theme.ChatDemoTheme
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ConversationUI(viewModel: ConversationViewModel) {
    val messages by viewModel.messages.collectAsState()
    ConversationUI(
        messages = messages,
        onSendClicked = { text -> viewModel.addMessage(text) },
        onFakeReplyClicked = { viewModel.addFakeReply() }
    )
}

@Composable
fun ConversationUI(
    messages: MessageList,
    onSendClicked: (String) -> Unit,
    onFakeReplyClicked: () -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                Toolbar(
                    onFakeReplyClicked = onFakeReplyClicked
                )
            },
            bottomBar = {
                BottomBar {
                    TextEntryBox(
                        onSendClicked = onSendClicked,
                        resetScroll = {
                            scope.launch {
                                scrollState.scrollToItem(0)
                            }
                        }
                    )
                }
            },
        ) { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                MessageListUI(messages, scrollState)
            }
        }
    }
}


@Composable
fun Toolbar(
    onFakeReplyClicked: () -> Unit
) {
    TopBar(
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(64.dp)
                )
            }
        },
        title = {
            Image(
                painter = painterResource(id = R.drawable.sample_avatar),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Text(
                text = OTHER_AUTHOR,
                style = MaterialTheme.typography.subtitle2.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        actions = {
            IconButton(onClick = { onFakeReplyClicked() }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(90f)
                )
            }
        }
    )
}

@Composable
fun MessageListUI(
    messages: MessageList,
    scrollState: LazyListState
) {
    LazyColumn(
        state = scrollState,
        reverseLayout = true,
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        itemsIndexed(messages) { _: Int, message: Message ->
            Column {
                val showSection = messages.isOldest(message) || messages.isPreviousMessageMoreThanAnHourApart(message)
                if (showSection) {
                    SectionHeader(message.timeInMillis)
                }

                val showTail = messages.isMostRecent(message)
                        || messages.isNextMessageFromDifferentAuthor(message)
                        || messages.isNextMessageMoreThan20SecondsApart(message)
                MessageUI(message, showTail, showSection)
            }
        }
    }
}

@Composable
fun SectionHeader(timeInMillis: Long) {
    val dateTime: LocalDateTime = Instant.ofEpochMilli(timeInMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val dayOfTheWeek = dateTime.format(DateTimeFormatter.ofPattern("EEEE"))
    val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(dayOfTheWeek)
            }
            append(" ")
            append(time)
        },
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle2,
    )
}

@Composable
fun TextEntryBox(
    onSendClicked: (String) -> Unit,
    resetScroll: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var value by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.weight(1f).semantics { contentDescription = "Message text field" },
            shape = RoundedCornerShape(32.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                onSendClicked(value)
                value = ""

                // Move scroll to bottom
                resetScroll()
                // Remove focus (which also hides keyboard)
                focusManager.clearFocus()
            },
            enabled = value.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "Send",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = if (value.isNotBlank()) {
                                listOf(
                                    Color(0xFFFF1278),
                                    Color(0xFFFE7168)
                                )
                            } else {
                                listOf(
                                    Color(0x80FF1278),
                                    Color(0x80FE7168)
                                )
                            }
                        ),
                        shape = CircleShape
                    )
                    .padding(start = 8.dp, end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChatDemoTheme {
        ConversationUI(
            messages = FakeData.previewMessages,
            onSendClicked = {},
            onFakeReplyClicked = {}
        )
    }
}
