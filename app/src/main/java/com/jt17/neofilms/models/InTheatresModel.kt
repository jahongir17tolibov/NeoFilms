package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class InTheatersInception(
    val items: List<InTheatresModel>?
)

@Parcelize
@Entity(tableName = "inTheaters_table")
data class InTheatresModel(
    var id: String,
    val title: String,
    val releaseState: String,
    val genres: String,
    val image: String,
    val plot: String,
    val contentRating: String,
    val imDbRating: String,
    val runtimeMins: String,
    val directors: String,
    val year: String,

    @PrimaryKey(autoGenerate = true)
    var inThID: Int
) : Parcelable

