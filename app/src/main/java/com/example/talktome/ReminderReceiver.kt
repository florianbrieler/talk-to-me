package com.example.talktome

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: return
        Log.d(TAG, "onReceive message=${'$'}message")
        val service = Intent(context, TtsService::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        ContextCompat.startForegroundService(context, service)
    }
    companion object {
        const val EXTRA_MESSAGE = "message"
        private const val TAG = "ReminderReceiver"
    }
}
