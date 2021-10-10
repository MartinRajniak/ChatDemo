package eu.rajniak.chat.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageUI(
    message: Message,
    showTail: Boolean,
    showSection: Boolean
) {
    val currentProfile = message.author == FakeData.CURRENT_AUTHOR

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (currentProfile) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(top = if (showSection) 0.dp else 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = if (currentProfile) {
                            MaterialTheme.colors.primary
                        } else {
                            MaterialTheme.colors.secondary
                        },
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = if (showTail && currentProfile) 0.dp else 16.dp,
                            bottomStart = if (!showTail || currentProfile) 16.dp else 0.dp
                        )
                    )
                    .fillMaxWidth(0.75f)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.body1.copy(
                        color = if (currentProfile) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSecondary
                        }
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}
