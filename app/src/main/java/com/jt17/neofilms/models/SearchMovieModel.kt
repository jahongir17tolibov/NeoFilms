package com.jt17.neofilms.models

class SearchModelInception(
    val results: List<SearchModel>?
)

data class SearchModel(
    var id: String,
    val title: String,
    val image: String,
    val description: String
)