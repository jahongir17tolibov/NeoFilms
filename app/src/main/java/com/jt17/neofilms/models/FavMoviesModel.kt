package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favourite_movies")
data class FavMoviesModel(
    val movieId: String,
    val title: String,
    val imDbRating: String,
    val year: String,
    val image: String,
    val crew: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int
) : Parcelable