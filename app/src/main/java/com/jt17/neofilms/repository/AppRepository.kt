package com.jt17.neofilms.repository

import com.jt17.neofilms.data.local.FilmsDao
import com.jt17.neofilms.data.remote.RemoteDataSource
import com.jt17.neofilms.models.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

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
                filmsDao.deleteAndInsertComingSoon(it)
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
                filmsDao.deleteAndInsertInTheatres(it)
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
                filmsDao.deleteAndInsertMostPopMovies(it)
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching most popular shows data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingMostPopShows(): Flow<Resource<List<MostPopTVShowsModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllMostPopTVs()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchMostPopShowsApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.deleteAndInsertMostPopTVs(it)
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetching box office movies data from data source and if has connection to network caching data to Room  */
    suspend fun fetchingBoxOffice(): Flow<Resource<List<BoxOfficeModel>>> = flow {
        emit(Resource.Loading())
        val localData = filmsDao.getAllBoxOfficeMovies()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = remoteDataSource.fetchBoxOfficeApi()
        if (remoteData.status == Resource.Status.SUCCESS) {
            remoteData.data?.let {
                filmsDao.deleteAndInsertBoxOfficeMovies(it)
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
                filmsDao.deleteAndInsertTopMovies(it)
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
                filmsDao.deleteAndInsertTop250Shows(it)
                emit(Resource.Success(it))
            }
        } else if (remoteData.status == Resource.Status.ERROR) {
            emit(Resource.Error(remoteData.message ?: "Unknown error occurred", localData))
        }
    }.flowOn(IO)

    /* fetch all info movies or shows */
    suspend fun fetchingAllFilmsData(id: String): Flow<Resource<TitleModel>> = flow {
        emit(Resource.Loading())
        emit(remoteDataSource.fetchTitleMovieById(id))
    }.flowOn(IO)

    /* fetch films all info from wiki */
    suspend fun fetchingFilmsWikiData(id: String): Flow<Resource<WikiModel>> = flow {
        emit(Resource.Loading())
        emit(remoteDataSource.fetchFilmsWikiApi(id))
    }.flowOn(IO)

    /* fetch tv series season and episodes data */
    suspend fun fetchingSeasonEpsData(id: String, seasonEps: String): Flow<Resource<SeasonModel>> =
        flow {
            emit(Resource.Loading())
            emit(remoteDataSource.fetchSeasonEpsApi(id, seasonEps))
        }.flowOn(IO)

    /* fetching search movies */
    suspend fun fetchingSearchMoviesData(search: String): Flow<Resource<List<SearchModel>>> =
        flow {
            emit(Resource.Loading())
            emit(remoteDataSource.fetchSearchMovieApi(search))
        }.flowOn(IO)

    /* fetching images in theaters in the room for this */
    fun fetchInTheatersImg(): Flow<List<String>> = filmsDao.getInTheatersImages()

    /* fetching images most popular movies in the room for this */
    fun fetchMostPopMoviesImg(): Flow<List<String>> = filmsDao.getMostPopMoviesImages()

    /* fetching images box office in the room for this */
    fun fetchBoxOfficeImg(): Flow<List<String>> = filmsDao.getBoxOfficeImages()

    /* fetching images most popular TV shows in the room for this */
    fun fetchMostPopShowsImg(): Flow<List<String>> = filmsDao.getMostPopTVsImages()

    /* fetching all In theaters movies data in the room for this */
    fun getAllInTheatersData(): Flow<List<InTheatresModel>> = filmsDao.getAllCachedInTheatersData()

    /* fetching all most popular movies data in the room for this */
    fun getAllMostPopMoviesData(): Flow<List<MostPopMoviesModel>> =
        filmsDao.getAllCachedMostPopMovies()

    /* fetching all vox office movies data in the room for this */
    fun getAllBoxOfficeData(): Flow<List<BoxOfficeModel>> = filmsDao.getAllCachedBoxOfficeMovies()

    /* fetching all vox office movies data in the room for this */
    fun getAllMostPopShowsData(): Flow<List<MostPopTVShowsModel>> =
        filmsDao.getAllCachedMostPopTVs()

    /* insert movie to fav movies */
    suspend fun insertFavMovie(favMoviesModel: FavMoviesModel) =
        filmsDao.insertFavMovies(favMoviesModel)

    /* insert tv show to fav series */
    suspend fun insertFavSeries(favSeriesModel: FavSeriesModel) =
        filmsDao.insertFavSeries(favSeriesModel)

    fun getAllFavMoviesData(): Flow<List<FavMoviesModel>> = filmsDao.getAllFavMovies()

    fun getAllFavSeriesData(): Flow<List<FavSeriesModel>> = filmsDao.getAllFavSeries()

    fun getFavMovieID(id: String?): Flow<FavMoviesModel> = filmsDao.getOneFavMovie(id)

    fun getFavSeriesID(id: String?): Flow<FavSeriesModel> = filmsDao.getOneFavSeries(id)

    suspend fun clearAllFavMovies() = filmsDao.deleteAllFavMovies()

    suspend fun clearAllFavSeries() = filmsDao.deleteAllFavSeries()

    suspend fun deleteOneFavMovie(favMoviesModel: FavMoviesModel) =
        filmsDao.deleteOneFavMovie(favMoviesModel)

    suspend fun deleteOneFavShow(favSeriesModel: FavSeriesModel) =
        filmsDao.deleteOneFavSeries(favSeriesModel)

}