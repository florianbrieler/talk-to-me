package com.example.talktome

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkRepository @Inject constructor(private val dao: TalkDao) {
    val talks = dao.getAll()

    suspend fun add(talk: Talk): Long = dao.insert(talk)

    suspend fun delete(talk: Talk) = dao.delete(talk)

    suspend fun update(talk: Talk) = dao.update(talk)
}
