package org.talktome

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Talk::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun talkDao(): TalkDao
}
