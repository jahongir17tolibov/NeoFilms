package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favourite_tv_shows")
data class FavSeriesModel(
    val seriesId: String,
    val title: String,
    val imDbRating: String,
    val year: String,
    val image: String,
    val crew: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int
) : Parcelable
