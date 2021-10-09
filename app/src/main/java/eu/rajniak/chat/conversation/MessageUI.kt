package eu.rajniak.chat.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import eu.rajniak.chat.data.FakeData

@Composable
fun MessageUI(
    message: Message,
    showTail: Boolean,
    showSection: Boolean
) {
    val currentProfile = remember {
        message.author == FakeData.CURRENT_AUTHOR
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (currentProfile) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(top = if (showSection) 0.dp else 8.dp)
        ) {
            if (showTail && !currentProfile) {
                Tail(currentProfile)
            } else {
                Spacer(modifier = Modifier.width(TailWidth))
            }
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
            if (showTail && currentProfile) {
                Tail(currentProfile)
            } else {
                Spacer(modifier = Modifier.width(TailWidth))
            }
        }
    }
}

@Composable
fun Tail(currentProfile: Boolean) {
    Column(
        modifier = Modifier
            .background(
                color = if (currentProfile) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                shape = if (!currentProfile) StartTriangleEdgeShape(10) else EndTriangleEdgeShape(10)
            )
            .width(TailWidth)
            .fillMaxHeight()
    ) {
    }
}

private val TailWidth = 8.dp

class StartTriangleEdgeShape(private val offset: Int) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f + 2 * offset + 2, y = size.height - offset)
            lineTo(x = 0f + 2 * offset + 2, y = size.height)
            lineTo(x = 0f + offset + 2, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}

class EndTriangleEdgeShape(private val offset: Int) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height - offset)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f + offset, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}
