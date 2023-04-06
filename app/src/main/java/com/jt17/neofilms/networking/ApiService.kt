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
    @GET("API/ComingSoon/{key}")
    suspend fun getComingSoonApi(@Path("key") key: String): Response<ComingSoonInception>

    //In theaters Getter
    @GET("API/InTheaters/{key}")
    suspend fun getInTheatresApi(@Path("key") key: String): Response<InTheatersInception>

    //Most Popular movies Getter
    @GET("API/MostPopularMovies/{key}")
    suspend fun getPopularMoviesApi(@Path("key") key: String): Response<PopMoviesInception>

    //Most Popular TVs Getter
    @GET("API/MostPopularTVs/{key}")
    suspend fun getPopularShowsApi(@Path("key") key: String): Response<PopShowsInception>

    //Box office movies Getter
    @GET("API/BoxOffice/{key}")
    suspend fun getBoxOfficeApi(@Path("key") key: String): Response<BoxOfficeInception>

    //Most Popular TVs Getter
    @GET("API/Top250Movies/{key}")
    suspend fun getTop250MoviesApi(@Path("key") key: String): Response<TopMoviesInception>

    //Most Popular TVs Getter
    @GET("API/Top250TVs/{key}")
    suspend fun getTop250TVsApi(@Path("key") key: String): Response<TopShowsInception>

    @GET("API/SearchMovie/{key}/{expression}")
    suspend fun getSearchMoviesApi(
        @Path("key") key: String,
        @Path("expression") expression: String?
    ): Response<SearchModelInception>

    @GET("API/SearchSeries/{key}/{expression}")
    suspend fun getSearchSeriesApi(
        @Path("key") key: String,
        @Path("expression") expression: String?
    ): Response<SearchModelInception>

    @GET("{lang}/API/Title/{key}/{id}")
    suspend fun getAllFilmsInfo(
        @Path("lang") lang: String,
        @Path("key") key: String,
        @Path("id") id: String
    ): Response<TitleModel>

    @GET("{lang}/API/Wikipedia/{key}/{id}")
    suspend fun getFilmsWikiApi(
        @Path("lang") lang: String,
        @Path("key") key: String,
        @Path("id") id: String
    ): Response<WikiModel>

    @GET("API/SeasonEpisodes/{key}/{id}/{seasonNumb}")
    suspend fun getSeasonEpsApi(
        @Path("key") key: String,
        @Path("id") id: String,
        @Path("seasonNumb") seasonNumb: String
    ): Response<SeasonModel>

}