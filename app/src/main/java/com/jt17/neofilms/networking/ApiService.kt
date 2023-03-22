package com.jt17.neofilms.networking

import com.jt17.neofilms.models.*
import com.jt17.neofilms.utils.Config
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//imdb main model
interface ApiService {

    //top films or tvs Getter
//    @GET("{func}/{key}")
//    fun getApi_topM(
//        @Path("key") key: String
//    ): Call<imdbApiModel<List<mainModel>>>

    //Coming Soon Slider Getter
    @GET("ComingSoon/{key}")
    suspend fun getComingSoonApi(@Path("key") key: String): Response<ComingSoonInception>

    //In theaters Getter
    @GET("InTheaters/{key}")
    suspend fun getInTheatresApi(@Path("key") key: String): Response<InTheatersInception>

    //Most Popular movies Getter
    @GET("MostPopularMovies/{key}")
    suspend fun getPopularMoviesApi(@Path("key") key: String): Response<PopMoviesInception>

    //Most Popular TVs Getter
    @GET("MostPopularTVs/{key}")
    suspend fun getPopularShowsApi(@Path("key") key: String): Response<PopShowsInception>

    //Most Popular TVs Getter
    @GET("Top250Movies/{key}")
    suspend fun getTop250MoviesApi(@Path("key") key: String): Response<TopMoviesInception>

    //Most Popular TVs Getter
    @GET("Top250TVs/{key}")
    suspend fun getTop250TVsApi(@Path("key") key: String): Response<TopShowsInception>

}