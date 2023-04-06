package com.jt17.neofilms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowsModel(
    val mainImg: Int? = null,
    val imagesList: List<String>? = null,
    val titleTxt: String
) : Parcelable
