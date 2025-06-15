package org.talktome

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import android.util.Log

private const val TAG = "Theme"

@Composable
fun TalkToMeTheme(content: @Composable () -> Unit) {
    Log.d(TAG, "TalkToMeTheme called")
    val colors = darkColorScheme()
    MaterialTheme(colorScheme = colors, content = content)
}
