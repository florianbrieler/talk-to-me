package com.example.talktome

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.util.Log

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "talks.db").build().also {
            Log.d(TAG, "provideDatabase")
        }

    @Provides
    fun provideTalkDao(db: AppDatabase): TalkDao = db.talkDao().also {
        Log.d(TAG, "provideTalkDao")
    }

    @Provides
    @Singleton
    fun provideScheduler(@ApplicationContext context: Context): TalkScheduler = TalkScheduler(context).also {
        Log.d(TAG, "provideScheduler")
    }
}

private const val TAG = "AppModule"
