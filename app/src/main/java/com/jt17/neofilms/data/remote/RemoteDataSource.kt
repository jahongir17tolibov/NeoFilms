package com.jt17.neofilms.data.remote

import com.jt17.neofilms.models.*
import com.jt17.neofilms.networking.ApiService
import com.jt17.neofilms.utils.Config
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    //fetching coming soon data's api
    suspend fun fetchComingSoonApi(): Resource<List<ComingSoonModel>> = try {
        val response = apiService.getComingSoonApi(Config.API_KEY1)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching in theaters data's api
    suspend fun fetchInTheatersApi(): Resource<List<InTheatresModel>> = try {
        val response = apiService.getInTheatresApi(Config.API_KEY1)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching most popular movies data's api
    suspend fun fetchMostPopMoviesApi(): Resource<List<MostPopMoviesModel>> = try {
        val response = apiService.getPopularMoviesApi(Config.API_KEY1)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching most popular shows data's api
    suspend fun fetchMostPopShowsApi(): Resource<List<MostPopTVShowsModel>> = try {
        val response = apiService.getPopularShowsApi(Config.API_KEY1)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching box office data's api
    suspend fun fetchBoxOfficeApi(): Resource<List<BoxOfficeModel>> = try {
        val response = apiService.getBoxOfficeApi(Config.API_KEY1)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching top 250 movies data's api
    suspend fun fetchTopMoviesApi(): Resource<List<Top250MoviesModel>> = try {
        val response = apiService.getTop250MoviesApi(Config.API_KEY3)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching top 250 TV shows data's api
    suspend fun fetchTopShowsApi(): Resource<List<Top250ShowsModel>> = try {
        val response = apiService.getTop250TVsApi(Config.API_KEY3)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching search movie api
    suspend fun fetchSearchMovieApi(search: String): Resource<List<SearchModel>> = try {
        val response = apiService.getSearchMoviesApi(Config.API_KEY3, search)
        Resource.Success(response.body()?.results!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    suspend fun fetchSearchSeriesApi(search: String): Resource<List<SearchModel>> = try {
        val response = apiService.getSearchSeriesApi(Config.API_KEY3, search)
        Resource.Success(response.body()?.results!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    suspend fun fetchTitleMovieById(id: String): Resource<TitleModel> = try {
        val response = apiService.getAllFilmsInfo("en", Config.API_KEY4, id)
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("No data available")
        } else {
            Resource.Error(response.message())
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    suspend fun fetchFilmsWikiApi(id: String): Resource<WikiModel> = try {
        val response = apiService.getFilmsWikiApi("en", Config.API_KEY4, id)
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("No data available")
        } else {
            Resource.Error(response.message())
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    suspend fun fetchSeasonEpsApi(id: String, seasonNumb: String): Resource<SeasonModel> = try {
        val response = apiService.getSeasonEpsApi(Config.API_KEY4, id, seasonNumb)
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("No data available")
        } else {
            Resource.Error(response.message())
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

}