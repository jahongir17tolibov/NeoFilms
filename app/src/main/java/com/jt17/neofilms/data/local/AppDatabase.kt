package com.jt17.neofilms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jt17.neofilms.models.*

@Database(
    entities = [ComingSoonModel::class, InTheatresModel::class, MostPopMoviesModel::class,
        MostPopTVShowsModel::class, Top250MoviesModel::class, Top250ShowsModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao

}