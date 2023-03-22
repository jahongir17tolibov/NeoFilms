package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class PopMoviesInception(
    val items: List<MostPopMoviesModel>?
)

@Parcelize
@Entity(tableName = "most_popular_movies_table")
data class MostPopMoviesModel(
    var id: String,
    val title: String,
    val image: String,
    val rank: String,
    val rankUpDown: String,
    val year: String,
    val imDbRating: String,
    val crew: String,

    @PrimaryKey(autoGenerate = true)
    var mostPopMoviesID: Int
) : Parcelable
