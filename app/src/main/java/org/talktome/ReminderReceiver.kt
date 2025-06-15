package org.talktome

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {
    private val TAG = "ReminderReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: return
        val service = Intent(context, TtsService::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        ContextCompat.startForegroundService(context, service)
    }
    companion object {
        const val EXTRA_MESSAGE = "message"
    }
}
