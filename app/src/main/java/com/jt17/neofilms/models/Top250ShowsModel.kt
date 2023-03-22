package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class TopShowsInception(
    val items: List<Top250ShowsModel>?
)

@Parcelize
@Entity(tableName = "top_250_shows_table")
data class Top250ShowsModel(
    var id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDbRating: String,

    @PrimaryKey(autoGenerate = true)
    var topShowsID: Int
) : Parcelable
