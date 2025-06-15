package org.talktome

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TalkDao {
    @Query("SELECT * FROM talks")
    fun getAll(): Flow<List<Talk>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(talk: Talk): Long

    @Delete
    suspend fun delete(talk: Talk)

    @Update
    suspend fun update(talk: Talk)
}
