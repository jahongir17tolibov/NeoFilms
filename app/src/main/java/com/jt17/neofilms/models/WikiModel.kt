package com.jt17.neofilms.models

data class WikiModel(
    val imDbId: String,
    val title: String,
    val type: String,
    val year: String,
    val plotShort: PlotShort?,
    val plotFull: PlotFull?
) {

    data class PlotShort(
        val html: String,
        val plainText: String
    )

    data class PlotFull(
        val html: String,
        val plainText: String
    )

}
