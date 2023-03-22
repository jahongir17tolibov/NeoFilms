package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class TopMoviesInception(
    val items: List<Top250MoviesModel>?
)

@Parcelize
@Entity(tableName = "top_250_movies_table")
data class Top250MoviesModel(
    var id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDbRating: String,

    @PrimaryKey(autoGenerate = true)
    var topMoviesID: Int
) : Parcelable
