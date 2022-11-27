package com.jt17.neofilms.ApiServices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetManager {
    var retrofit: Retrofit? = null
    var api: topM_Api? = null

    fun getApiService(): topM_Api {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://imdb-api.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(topM_Api::class.java)
        }
        return api!!
    }

}