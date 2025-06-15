package org.talktome

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TtsManager @Inject constructor(@ApplicationContext context: Context) : TextToSpeech.OnInitListener {
    private val tts = TextToSpeech(context, this)
    private var ready = false
    private val pending = mutableListOf<String>()
    private val TAG = "TtsManager"

    override fun onInit(status: Int) {
        Log.d(TAG, "onInit status=${'$'}status")
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
            ready = true
            // Flush any messages that arrived before initialization completed
            pending.forEach { text ->
                tts.speak(text, TextToSpeech.QUEUE_ADD, null, UUID.randomUUID().toString())
            }
            pending.clear()
        }
    }

    fun speak(text: String) {
        Log.d(TAG, "speak called")
        if (ready) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, UUID.randomUUID().toString())
        } else {
            pending.add(text)
        }
    }
}
