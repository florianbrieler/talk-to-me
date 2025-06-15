package org.talktome

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun TalkToMeTheme(content: @Composable () -> Unit) {
    val colors = darkColorScheme()
    MaterialTheme(colorScheme = colors, content = content)
}
