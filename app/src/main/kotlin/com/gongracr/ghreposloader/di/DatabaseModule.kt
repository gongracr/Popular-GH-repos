package com.gongracr.ghreposloader.di

import android.content.Context
import androidx.room.Room
import com.gongracr.persistence.dao.GHProjectsDao
import com.gongracr.persistence.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "gh-projects-db"
        ).build()
    }

    @Provides
    fun provideGHProjectsDao(database: AppDatabase): GHProjectsDao {
        return database.ghProjectsDao()
    }
}