package eu.rajniak.chat.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFC5004D),
    primaryVariant = Color(0xFFC5004D),
    secondary = Color(0xFFCCCCCC),

    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Gray,
    onSurface = Color(0xFFB3BBC3),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFFF1278),
    primaryVariant = Color(0xFFFF1278),
    secondary = Color(0xFFF2F5FB),

    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Gray,
    onSurface = Color(0xFFB3BBC3),
)

@Composable
fun ChatDemoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
