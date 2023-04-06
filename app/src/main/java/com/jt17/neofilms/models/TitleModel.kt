package com.jt17.neofilms.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject


data class TitleModel(
    var id: String,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val image: String,
    val plot: String,
    val year: String,
    val plotLocal: String?,
    val contentRating: String,
    val imDbRating: String,
    val metacriticRating: String,
    val runtimeStr: String? = null,
    val directors: String? = null,
    val awards: String,
    val type: String,
    val stars: String,
    val writers: String,
    val companies: String,
    val countries: String,
    val languages: String,

    val keywordList: List<String>,
    val actorList: List<ActorsModel>?,
    val boxOffice: BoxOfficeTitlesModel?,
    val seasons: List<String>?,
    val tvSeriesInfo: TvSeriesInfoModel?,

    ) {

//    val boxOfficeValue: String?
//        get() = when (boxOffice) {
//            is JsonObject -> boxOffice.asJsonObject.get("Value").asString
//            is JsonArray -> boxOffice[0].asJsonObject.get("Value").asString
//            else -> null
//        }


    data class ActorsModel(
        val id: String,
        val image: String,
        val asCharacter: String,
        val name: String
    )

    data class BoxOfficeTitlesModel(
        val budget: String,
        val cumulativeWorldwideGross: String,
        val grossUSA: String,
        val openingWeekendUSA: String
    )

    data class TvSeriesInfoModel(
        val yearEnd: String,
        val creators: String
    )

}
