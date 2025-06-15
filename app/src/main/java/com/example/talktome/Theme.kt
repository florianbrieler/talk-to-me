package com.example.talktome

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import android.util.Log

@Composable
fun TalkToMeTheme(content: @Composable () -> Unit) {
    Log.d(TAG, "TalkToMeTheme")
    val colors = darkColorScheme()
    MaterialTheme(colorScheme = colors, content = content)
}

private const val TAG = "Theme"
