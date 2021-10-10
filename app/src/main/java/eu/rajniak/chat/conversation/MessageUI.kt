package eu.rajniak.chat.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MessageUI(
    message: Message,
    showTail: Boolean,
    showSection: Boolean
) {
    val currentProfile = message.author == FakeData.CURRENT_AUTHOR

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (showSection) 0.dp else 8.dp),
        contentAlignment = if (currentProfile) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (currentProfile) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.secondary
                    },
                    shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomEnd = if (showTail && currentProfile) 0.dp else 24.dp,
                        bottomStart = if (!showTail || currentProfile) 24.dp else 0.dp
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
                modifier = Modifier.padding(16.dp)
            )
            if (currentProfile) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Status",
                        tint = Color(0xFFCCBBCB),
                        modifier = Modifier
                            .size(12.dp)
                    )
                }
            }
        }
    }
}
