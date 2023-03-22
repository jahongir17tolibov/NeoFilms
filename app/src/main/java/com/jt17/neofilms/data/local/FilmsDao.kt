package com.jt17.neofilms.data.local

import androidx.room.*
import com.jt17.neofilms.models.*

@Dao
interface FilmsDao {

    /** For fetching data from api and save to room **/

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

    @Query("SELECT * FROM inTheaters_table")
    fun getAllInTheaters(): List<InTheatresModel>

    @Delete
    fun deleteInTheaters(inTheatersData: List<InTheatresModel>)

    /** Most popular movies catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMostPopMovies(data: List<MostPopMoviesModel>)

    @Query("SELECT * FROM most_popular_movies_table")
    fun getAllMostPopMovies(): List<MostPopMoviesModel>

    @Delete
    fun deleteMostPopMovies(mostPopMoviesData: List<MostPopMoviesModel>)

    /** Most popular tv shows catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMostPopTVs(data: List<MostPopTVShowsModel>)

    @Query("SELECT * FROM most_popular_shows_table")
    fun getAllMostPopShows(): List<MostPopTVShowsModel>

    @Delete
    fun deleteMostPopShows(mostPopShowsData: List<MostPopTVShowsModel>)

    /** Top 250 movies catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTop250Movies(data: List<Top250MoviesModel>)

    @Query("SELECT * FROM top_250_movies_table")
    fun getAllTopMovies(): List<Top250MoviesModel>

    @Delete
    fun deleteTopMovies(TopMoviesData: List<Top250MoviesModel>)

    /** top 250 TV shows catalog **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTop250Shows(data: List<Top250ShowsModel>)

    @Query("SELECT * FROM top_250_shows_table")
    fun getAllTop250Shows(): List<Top250ShowsModel>

    @Delete
    fun deleteTop250Shows(TopShowsData: List<Top250ShowsModel>)

}