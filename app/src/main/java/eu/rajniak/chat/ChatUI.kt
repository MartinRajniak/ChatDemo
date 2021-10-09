package eu.rajniak.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.derivedWindowInsetsTypeOf
import com.google.accompanist.insets.rememberInsetsPaddingValues
import eu.rajniak.chat.ui.theme.ChatDemoTheme

@Composable
fun ChatUI() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
        ) { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                MessageList()
            }
        }
    }
}

@Composable
fun TopBar(
    navigationIcon: @Composable (() -> Unit),
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyStart = true,
            applyTop = true,
            applyEnd = true,
        ),
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = TitleIconModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.high,
                content = navigationIcon
            )
        }

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            title()
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(
                Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
}

@Composable
fun TopBar() {
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
            Text(text = "Sarah")
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }
    )
}

private val AppBarHorizontalPadding = 4.dp

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(56.dp - AppBarHorizontalPadding)

@Composable
fun MessageList() {
    Text("MessageList")
}

@Composable
fun BottomBar() {
    val ime = LocalWindowInsets.current.ime
    val navBars = LocalWindowInsets.current.navigationBars
    val insets = remember(ime, navBars) { derivedWindowInsetsTypeOf(ime, navBars) }
    BottomAppBar(
        contentPadding = rememberInsetsPaddingValues(
            insets = insets,
            applyStart = true,
            applyBottom = true,
            applyEnd = true,
            applyTop = false,
            additionalStart = 16.dp,
            additionalEnd = 16.dp,
            additionalBottom = 16.dp,
            additionalTop = 16.dp
        ),
        backgroundColor = Color.White
    ) {
        TextEntryBox()
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
        ChatUI()
    }
}
