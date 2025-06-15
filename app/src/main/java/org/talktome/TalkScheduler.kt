package org.talktome

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkScheduler @Inject constructor(@ApplicationContext private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(talk: Talk) {
        val pi = pendingIntent(talk)
        when (talk.triggerType) {
            TriggerType.TIME_INTERVAL -> {
                val interval = (talk.intervalMinutes ?: return) * 60 * 1000L
                val at = System.currentTimeMillis() + interval
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, at, interval, pi)
            }
            TriggerType.TIME_SPECIFIC -> {
                val time = talk.specificTimeMillis ?: return
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pi)
            }
            TriggerType.LOCATION -> {
                // location not implemented
            }
        }
    }

    fun cancel(talk: Talk) {
        alarmManager.cancel(pendingIntent(talk))
    }

    private fun pendingIntent(talk: Talk): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_MESSAGE, talk.message)
        }
        return PendingIntent.getBroadcast(
            context,
            talk.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
