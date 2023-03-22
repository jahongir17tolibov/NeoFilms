package com.jt17.neofilms.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetManager {
    var retrofit: Retrofit? = null
    var api: ApiService? = null

    fun getApiService(): ApiService {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://imdb-api.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(ApiService::class.java)
        }
        return api!!
    }


}