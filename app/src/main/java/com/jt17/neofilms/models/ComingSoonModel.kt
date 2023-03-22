package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class ComingSoonInception(
    val items: List<ComingSoonModel>?
)

@Parcelize
@Entity(tableName = "coming_soon_table")
data class ComingSoonModel(
    var id: String,
    val releaseState: String,
    val genres: String,
    val title: String,
    val image: String,
    val stars: String,

    @PrimaryKey(autoGenerate = true)
    var comingSoonID: Int
) : Parcelable
