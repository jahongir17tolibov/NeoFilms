package com.jt17.neofilms.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class BoxOfficeInception(
    val items: List<BoxOfficeModel>?
)

@Parcelize
@Entity(tableName = "box_office_table")
data class BoxOfficeModel(
    val id: String,
    val rank: String,
    val title: String,
    val image: String,
    val weekend: String,
    val gross: String,
    val weeks: String,

    @PrimaryKey(autoGenerate = true)
    var boxOfficeID: Int
) : Parcelable
