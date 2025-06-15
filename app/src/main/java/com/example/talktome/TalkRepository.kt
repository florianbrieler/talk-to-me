package com.example.talktome

import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log

@Singleton
class TalkRepository @Inject constructor(private val dao: TalkDao) {
    val talks = dao.getAll()

    suspend fun add(talk: Talk): Long {
        Log.d(TAG, "add talk=${'$'}talk")
        return dao.insert(talk)
    }

    suspend fun delete(talk: Talk) {
        Log.d(TAG, "delete talk=${'$'}talk")
        dao.delete(talk)
    }

    suspend fun update(talk: Talk) {
        Log.d(TAG, "update talk=${'$'}talk")
        dao.update(talk)
    }

    companion object {
        private const val TAG = "TalkRepository"
    }
}
