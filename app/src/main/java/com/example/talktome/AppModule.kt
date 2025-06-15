package com.example.talktome

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "talks.db").build()

    @Provides
    fun provideTalkDao(db: AppDatabase): TalkDao = db.talkDao()

    @Provides
    @Singleton
    fun provideScheduler(@ApplicationContext context: Context): TalkScheduler = TalkScheduler(context)
}
