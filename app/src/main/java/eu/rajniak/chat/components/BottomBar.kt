package eu.rajniak.chat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.derivedWindowInsetsTypeOf
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun BottomBar(
    content: @Composable () -> Unit
) {
    val ime = LocalWindowInsets.current.ime
    val navBars = LocalWindowInsets.current.navigationBars
    val insets = remember(ime, navBars) { derivedWindowInsetsTypeOf(ime, navBars) }
    Surface(
        color = Color.White,
        elevation = AppBarDefaults.BottomAppBarElevation
    ) {
        Row(
            modifier = Modifier.padding(
                rememberInsetsPaddingValues(
                    insets = insets,
                    applyStart = true,
                    applyBottom = true,
                    applyEnd = true,
                    applyTop = false,
                    additionalStart = 16.dp,
                    additionalEnd = 16.dp,
                    additionalBottom = 16.dp,
                    additionalTop = 16.dp
                )
            )
        ) {
            content()
        }
    }
}
