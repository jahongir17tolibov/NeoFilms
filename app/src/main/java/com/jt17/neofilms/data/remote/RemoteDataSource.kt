package com.jt17.neofilms.data.remote

import com.jt17.neofilms.models.*
import com.jt17.neofilms.networking.ApiService
import com.jt17.neofilms.utils.Config
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    //fetching coming soon data's api
    suspend fun fetchComingSoonApi(): Resource<ComingSoonInception> = try {
        val response = apiService.getComingSoonApi(Config.API_KEY2)
        Resource.Success(response.body()!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching in theaters data's api
    suspend fun fetchInTheatersApi(): Resource<List<InTheatresModel>> = try {
        val response = apiService.getInTheatresApi(Config.API_KEY2)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching most popular movies data's api
    suspend fun fetchMostPopMoviesApi(): Resource<List<MostPopMoviesModel>> = try {
        val response = apiService.getPopularMoviesApi(Config.API_KEY3)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching most popular shows data's api
    suspend fun fetchMostPopShowsApi(): Resource<List<MostPopTVShowsModel>> = try {
        val response = apiService.getPopularShowsApi(Config.API_KEY3)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching top 250 movies data's api
    suspend fun fetchTopMoviesApi(): Resource<List<Top250MoviesModel>> = try {
        val response = apiService.getTop250MoviesApi(Config.API_KEY6)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

    //fetching top 250 TV shows data's api
    suspend fun fetchTopShowsApi(): Resource<List<Top250ShowsModel>> = try {
        val response = apiService.getTop250TVsApi(Config.API_KEY6)
        Resource.Success(response.body()?.items!!)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An error occurred")
    }

}