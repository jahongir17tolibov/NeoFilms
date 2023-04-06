package com.jt17.neofilms.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jt17.neofilms.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    /** For fetching data from api and save to room **/
    @Query("DELETE FROM coming_soon_table")
    fun deleteAllComingSoon()

    @Transaction
    fun deleteAndInsertComingSoon(data: List<ComingSoonModel>) {
        deleteAllComingSoon()
        insertComingSoon(data)
    }

    /** Coming soon catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComingSoon(data: List<ComingSoonModel>)

    @Query("SELECT * FROM coming_soon_table")
    fun getAllComingSoon(): List<ComingSoonModel>

    @Delete
    fun deleteComingSoon(comingSoonData: List<ComingSoonModel>)

    /** InTheaters catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInTheaters(data: List<InTheatresModel>)

    @Query("DELETE FROM inTheaters_table")
    fun deleteAllInTheatres()

    @Query("SELECT * FROM inTheaters_table")
    fun getAllInTheaters(): List<InTheatresModel>

    @Delete
    fun deleteInTheaters(inTheatersData: List<InTheatresModel>)

    @Transaction
    fun deleteAndInsertInTheatres(data: List<InTheatresModel>) {
        deleteAllInTheatres()
        insertInTheaters(data)
    }

    /** Most popular movies catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMostPopMovies(data: List<MostPopMoviesModel>)

    @Query("DELETE FROM most_popular_movies_table")
    fun deleteAllMostPopMovies()

    @Query("SELECT * FROM most_popular_movies_table")
    fun getAllMostPopMovies(): List<MostPopMoviesModel>

    @Delete
    fun deleteMostPopMovies(mostPopMoviesData: List<MostPopMoviesModel>)

    @Transaction
    fun deleteAndInsertMostPopMovies(data: List<MostPopMoviesModel>) {
        deleteAllMostPopMovies()
        insertMostPopMovies(data)
    }

    /** Most popular tv shows catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMostPopTVs(data: List<MostPopTVShowsModel>)

    @Query("DELETE FROM most_popular_shows_table")
    fun deleteAllMostPopTVs()

    @Query("SELECT * FROM most_popular_shows_table")
    fun getAllMostPopTVs(): List<MostPopTVShowsModel>

    @Delete
    fun deleteMostPopTVs(mostPopShowsData: List<MostPopTVShowsModel>)

    @Transaction
    fun deleteAndInsertMostPopTVs(data: List<MostPopTVShowsModel>) {
        deleteAllMostPopTVs()
        insertMostPopTVs(data)
    }

    /** box office movies catalog **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBoxOfficeMovies(data: List<BoxOfficeModel>)

    @Query("DELETE FROM box_office_table")
    fun deleteAllBoxOfficeMovies()

    @Query("SELECT * FROM box_office_table")
    fun getAllBoxOfficeMovies(): List<BoxOfficeModel>

    @Delete
    fun deleteBoxOfficeMovies(boxOfficeData: List<BoxOfficeModel>)

    @Transaction
    fun deleteAndInsertBoxOfficeMovies(data: List<BoxOfficeModel>) {
        deleteAllBoxOfficeMovies()
        insertBoxOfficeMovies(data)
    }

    /** Top 250 movies catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTop250Movies(data: List<Top250MoviesModel>)

    @Query("DELETE FROM top_250_movies_table")
    fun deleteAllTopMovies()

    @Query("SELECT * FROM top_250_movies_table")
    fun getAllTopMovies(): List<Top250MoviesModel>

    @Delete
    fun deleteTopMovies(TopMoviesData: List<Top250MoviesModel>)

    @Transaction
    fun deleteAndInsertTopMovies(data: List<Top250MoviesModel>) {
        deleteAllTopMovies()
        insertTop250Movies(data)
    }

    /** top 250 TV shows catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTop250Shows(data: List<Top250ShowsModel>)

    @Query("DELETE FROM top_250_shows_table")
    fun deleteAllTop250Shows()

    @Query("SELECT * FROM top_250_shows_table")
    fun getAllTop250Shows(): List<Top250ShowsModel>

    @Delete
    fun deleteTop250Shows(TopShowsData: List<Top250ShowsModel>)

    @Transaction
    fun deleteAndInsertTop250Shows(data: List<Top250ShowsModel>) {
        deleteAllTop250Shows()
        insertTop250Shows(data)
    }

    /** get image for movies fragment **/

    @Query("SELECT image FROM inTheaters_table")
    fun getInTheatersImages(): Flow<List<String>>

    @Query("SELECT image FROM most_popular_movies_table")
    fun getMostPopMoviesImages(): Flow<List<String>>

    @Query("SELECT image FROM most_popular_shows_table")
    fun getMostPopTVsImages(): Flow<List<String>>

    @Query("SELECT image FROM box_office_table")
    fun getBoxOfficeImages(): Flow<List<String>>

    @Query("SELECT image FROM top_250_shows_table")
    fun getTopShowsImages(): Flow<List<String>>

    @Query("SELECT * FROM inTheaters_table")
    fun getAllCachedInTheatersData(): Flow<List<InTheatresModel>>

    @Query("SELECT * FROM most_popular_movies_table")
    fun getAllCachedMostPopMovies(): Flow<List<MostPopMoviesModel>>

    @Query("SELECT * FROM box_office_table")
    fun getAllCachedBoxOfficeMovies(): Flow<List<BoxOfficeModel>>

    @Query("SELECT * FROM most_popular_shows_table")
    fun getAllCachedMostPopTVs(): Flow<List<MostPopTVShowsModel>>

    /** favorite movies and tv shows catalog **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovies(favMoviesModel: FavMoviesModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavSeries(favSeriesModel: FavSeriesModel)

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavMovies(): Flow<List<FavMoviesModel>>

    @Query("SELECT * FROM favourite_tv_shows")
    fun getAllFavSeries(): Flow<List<FavSeriesModel>>

    @Query("SELECT * FROM favourite_movies WHERE movieId =:id")
    fun getOneFavMovie(id: String?): Flow<FavMoviesModel>

    @Query("SELECT * FROM favourite_tv_shows WHERE seriesId =:id")
    fun getOneFavSeries(id: String?): Flow<FavSeriesModel>

    @Query("DELETE FROM favourite_movies")
    suspend fun deleteAllFavMovies()

    @Query("DELETE FROM favourite_tv_shows")
    suspend fun deleteAllFavSeries()

    @Delete
    suspend fun deleteOneFavMovie(favMoviesModel: FavMoviesModel)

    @Delete
    suspend fun deleteOneFavSeries(favSeriesModel: FavSeriesModel)

}