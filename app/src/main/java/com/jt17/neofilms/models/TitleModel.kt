package com.jt17.neofilms.models

data class TitleModel(
    var id: String,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val image: String,
    val plot: String,
    val plotLocal: String?,
    val contentRating: String,
    val imDbRating: String,
    val metacriticRating: String,
    val runtimeStr: String,
    val directors: String,
    val awards: String,
    val type: String,
    val stars: String,
    val writers: String,
    val companies: String,
    val countries: String,

    val keywordList: List<String>,
    val actorList: List<ActorsModel>?,
    val directorsList: List<DirectorsModel>?,
    val boxOffice: List<BoxOfficeTitlesModel>?,
    val similars: List<SimilarsModel>?

    ) {

    data class ActorsModel(
        val id: String,
        val image: String,
        val asCharacter: String,
        val name: String
    )

    data class DirectorsModel(
        val id: String,
        val name: String
    )

    data class BoxOfficeTitlesModel(
        val budget: String,
        val cumulativeWorldwideGross: String,
        val grossUSA: String,
        val openingWeekendUSA: String
    )

    data class SimilarsModel(
        val id: String,
        val imDbRating: String,
        val image: String,
        val title: String
    )
}
