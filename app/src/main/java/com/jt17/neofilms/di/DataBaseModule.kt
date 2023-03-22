package com.jt17.neofilms.di

import android.content.Context
import androidx.room.Room
import com.jt17.neofilms.data.local.AppDatabase
import com.jt17.neofilms.data.local.FilmsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @[Provides Singleton]
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "NeoFilms.db"
        ).build()

    @Provides
    fun provideFilmsDao(appDatabase: AppDatabase): FilmsDao = appDatabase.filmsDao()

}