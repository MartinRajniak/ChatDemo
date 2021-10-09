package eu.rajniak.chat.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.derivedWindowInsetsTypeOf
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun BottomBar(
    content: @Composable RowScope.() -> Unit
) {
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
        content()
    }
}
