package com.jt17.neofilms.repository

import android.util.Log
import com.jt17.neofilms.data.local.FilmsDao
import com.jt17.neofilms.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.jt17.neofilms.models.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn

@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val filmsDao: FilmsDao
) {

    /* fetching coming soon data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingComingSoon(): Flow<Resource<List<ComingSoonModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllComingSoon()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchComingSoonApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteComingSoon(it)
                    insertComingSoon(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching in theaters data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingInTheaters(): Flow<Resource<List<InTheatresModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllInTheaters()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchInTheatersApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteInTheaters(it)
                    insertInTheaters(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching most popular movies data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingMostPopMovies(): Flow<Resource<List<MostPopMoviesModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllMostPopMovies()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchMostPopMoviesApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteMostPopMovies(it)
                    insertMostPopMovies(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching most popular shows data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingMostPopShows(): Flow<Resource<List<MostPopTVShowsModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllMostPopShows()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchMostPopShowsApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteMostPopShows(it)
                    insertMostPopTVs(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching top 250 movies data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingTopMovies(): Flow<Resource<List<Top250MoviesModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllTopMovies()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchTopMoviesApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteTopMovies(it)
                    insertTop250Movies(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching top 250 TVs data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingTopShows(): Flow<Resource<List<Top250ShowsModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllTop250Shows()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchTopShowsApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.run {
                    deleteTop250Shows(it)
                    insertTop250Shows(it)
                }
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

}