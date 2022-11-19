package com.jt17.neofilms.models

data class top250_moviesModel(
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDbRating: String
)
