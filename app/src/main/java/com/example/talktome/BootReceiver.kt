package com.example.talktome

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: TalkRepository
    @Inject lateinit var scheduler: TalkScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Re-schedule all enabled talks
            CoroutineScope(Dispatchers.Default).launch {
                repository.talks.first().filter { it.enabled }.forEach { scheduler.schedule(it) }
            }
        }
    }
}
