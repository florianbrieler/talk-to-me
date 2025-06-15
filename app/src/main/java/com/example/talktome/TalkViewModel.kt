package com.example.talktome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TalkViewModel @Inject constructor(
    private val repository: TalkRepository,
    private val scheduler: TalkScheduler,
    private val tts: TtsManager
) : ViewModel() {
    val talks: StateFlow<List<Talk>> = repository.talks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTalk(message: String, intervalMinutes: Int) {
        viewModelScope.launch {
            val talk = Talk(message = message, intervalMinutes = intervalMinutes)
            val id = repository.add(talk)
            scheduler.schedule(talk.copy(id = id))
        }
    }
    fun speak(talk: Talk) {
        tts.speak(talk.message)
    }

    fun deleteTalk(talk: Talk) {
        viewModelScope.launch {
            repository.delete(talk)
            scheduler.cancel(talk)
        }
    }

    fun toggleEnabled(talk: Talk, enabled: Boolean) {
        viewModelScope.launch {
            val updated = talk.copy(enabled = enabled)
            repository.update(updated)
            if (enabled) scheduler.schedule(updated) else scheduler.cancel(updated)
        }
    }
}
