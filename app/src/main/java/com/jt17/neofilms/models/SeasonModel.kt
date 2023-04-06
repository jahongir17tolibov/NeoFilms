package com.jt17.neofilms.models

data class SeasonModel(
    val imDbId: String,
    val title: String,
    val year: String,
    val episodes: List<EpisodesModel>?,
) {

    data class EpisodesModel(
        val id: String,
        val seasonNumber: String,
        val episodeNumber: String,
        val year: String,
        val title: String,
        val image: String,
        val released: String,
        val imDbRating: String,
    )

}
