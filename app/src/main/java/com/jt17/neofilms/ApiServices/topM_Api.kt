package com.jt17.neofilms.ApiServices

import com.jt17.neofilms.models.NestedModel
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//imdb main model
interface topM_Api {
    @GET("{func}/{key}")
    fun getApi_topM(
        @Path("func") func: String,
        @Path("key") key: String
    ): Call<imdbApiModel<List<mainModel>>>

    @GET("{func}/{key}")
    fun getInTheatresApi(
        @Path("func") func: String,
        @Path("key") key: String
    ): Call<imdbApiModel<List<NestedModel.InTheatresModel>>>
}