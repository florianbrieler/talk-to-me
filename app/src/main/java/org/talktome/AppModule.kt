package org.talktome

import android.content.Context
import androidx.room.Room
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val TAG = "AppModule"
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "talks.db").build().also {
            Log.d(TAG, "provideDatabase called")
        }

    @Provides
    fun provideTalkDao(db: AppDatabase): TalkDao = db.talkDao().also {
        Log.d(TAG, "provideTalkDao called")
    }

    @Provides
    @Singleton
    fun provideScheduler(@ApplicationContext context: Context): TalkScheduler = TalkScheduler(context).also {
        Log.d(TAG, "provideScheduler called")
    }
}
