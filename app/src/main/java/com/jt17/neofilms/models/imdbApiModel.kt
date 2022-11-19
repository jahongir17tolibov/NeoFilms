package com.jt17.neofilms.models

data class imdbApiModel<T>(
    val errorMessage: String,
    val items: T
)
