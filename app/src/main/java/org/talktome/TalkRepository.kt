package org.talktome

import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log

@Singleton
class TalkRepository @Inject constructor(private val dao: TalkDao) {
    private val TAG = "TalkRepository"
    val talks = dao.getAll()

    suspend fun add(talk: Talk): Long {
        Log.d(TAG, "add called")
        return dao.insert(talk)
    }

    suspend fun delete(talk: Talk) {
        Log.d(TAG, "delete called")
        dao.delete(talk)
    }

    suspend fun update(talk: Talk) {
        Log.d(TAG, "update called")
        dao.update(talk)
    }
}
