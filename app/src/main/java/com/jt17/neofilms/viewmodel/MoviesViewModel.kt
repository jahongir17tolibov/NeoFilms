package com.jt17.neofilms.viewmodel

import androidx.lifecycle.*
import com.jt17.neofilms.models.*
import com.jt17.neofilms.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _inTheatersImageList = MutableLiveData<List<String>>()
    val inTheatersImageList: LiveData<List<String>> = _inTheatersImageList

    fun getInTheatersImg() = viewModelScope.launch {
        appRepository.fetchInTheatersImg().collect {
            _inTheatersImageList.value = it
        }
    }

    private val _mostPopMoviesImageList = MutableLiveData<List<String>>()
    val mostPopMoviesImageList: LiveData<List<String>> = _mostPopMoviesImageList

    fun getMostPopMoviesImg() = viewModelScope.launch {
        appRepository.fetchMostPopMoviesImg().collect {
            _mostPopMoviesImageList.value = it
        }
    }

    private val _boxOfficeImageList = MutableLiveData<List<String>>()
    val boxOfficeImageList: LiveData<List<String>> = _boxOfficeImageList

    fun getBoxOfficeImg() = viewModelScope.launch {
        appRepository.fetchBoxOfficeImg().collect {
            _boxOfficeImageList.value = it
        }
    }

    private val _topMoviesList =
        MutableStateFlow<Resource<List<Top250MoviesModel>>>(Resource.Loading())
    val topMoviesList: StateFlow<Resource<List<Top250MoviesModel>>> = _topMoviesList

    fun getTopMoviesData() = viewModelScope.launch {
        appRepository.fetchingTopMovies().collect {
            _topMoviesList.value = it
        }
    }

    private val _title = MutableStateFlow<Resource<TitleModel>>(Resource.Loading())
    val title: StateFlow<Resource<TitleModel>>
        get() = _title

    fun getAllFilmsInfo(id: String) = viewModelScope.launch {
        appRepository.fetchingAllFilmsData(id).collect {
            _title.value = it
        }
    }

    private val _wikiData = MutableLiveData<Resource<WikiModel>>()
    val wikiData: LiveData<Resource<WikiModel>> = _wikiData

    fun getFilmsWiki(id: String) = viewModelScope.launch {
        appRepository.fetchingFilmsWikiData(id).collect {
            _wikiData.value = it
        }
    }

    //caching all in theaters data from repository
    val getAllInTheatresData: LiveData<List<InTheatresModel>> =
        appRepository.getAllInTheatersData().asLiveData()

    //caching all most popular movies data from repository
    val getAllMostPopMoviesData: LiveData<List<MostPopMoviesModel>> =
        appRepository.getAllMostPopMoviesData().asLiveData()

    //caching all box office movies data from repository
    val getAllBoxOfficeData: LiveData<List<BoxOfficeModel>> =
        appRepository.getAllBoxOfficeData().asLiveData()

    fun insertFavMovie(favMoviesModel: FavMoviesModel) = viewModelScope.launch {
        appRepository.insertFavMovie(favMoviesModel)
    }

    val getAllFavMovies: LiveData<List<FavMoviesModel>> =
        appRepository.getAllFavMoviesData().asLiveData()

    fun clearAllFavMovies() = viewModelScope.launch {
        appRepository.clearAllFavMovies()
    }

    fun deleteOneFavMovie(favMoviesModel: FavMoviesModel) = viewModelScope.launch {
        appRepository.deleteOneFavMovie(favMoviesModel)
    }

    fun getFavMovieID(id: String?): LiveData<FavMoviesModel> =
        appRepository.getFavMovieID(id).asLiveData()

}