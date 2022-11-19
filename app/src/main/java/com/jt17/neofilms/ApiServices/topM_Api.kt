package com.jt17.neofilms.ApiServices

import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.top250_moviesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface topM_Api {
    @GET("Top250Movies/{key}")
    fun getApi_topM(@Path("key") key: String): Call<imdbApiModel<List<top250_moviesModel>>>
}