package com.jt17.neofilms.ApiServices

import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface topM_Api {
    @GET("{func}/{key}")
    fun getApi_topM(
        @Path("func") func: String,
        @Path("key") key: String
    ): Call<imdbApiModel<List<mainModel>>>
}//Top250Movies