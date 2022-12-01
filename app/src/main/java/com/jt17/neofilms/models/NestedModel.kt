package com.jt17.neofilms.models

data class NestedModel(
    val titleName: String = "owhiuw",
    val modelList: List<InTheatresModel>
) {
    data class InTheatresModel(
        val title: String,
        val releaseState: String,
        val genres: String,
        val image: String
    )
}


