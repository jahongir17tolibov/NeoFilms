package com.jt17.neofilms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jt17.neofilms.models.*
import com.jt17.neofilms.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _comingSoonList =
        MutableStateFlow<Resource<List<ComingSoonModel>>>(Resource.Loading())
    val comingSoonList: StateFlow<Resource<List<ComingSoonModel>>> = _comingSoonList

    fun getComingSoonData() = viewModelScope.launch {
        appRepository.fetchingComingSoon().collect {
            _comingSoonList.value = it
        }
    }

    private val _inTheatersList =
        MutableStateFlow<Resource<List<InTheatresModel>>>(Resource.Loading())
    val inTheatersList: StateFlow<Resource<List<InTheatresModel>>> = _inTheatersList

    fun getInTheatersData() = viewModelScope.launch {
        appRepository.fetchingInTheaters().collect {
            _inTheatersList.value = it
        }
    }

    private val _mostPopMoviesList =
        MutableStateFlow<Resource<List<MostPopMoviesModel>>>(Resource.Loading())
    val mostPopMoviesList: StateFlow<Resource<List<MostPopMoviesModel>>> = _mostPopMoviesList

    fun getMostPopMoviesData() = viewModelScope.launch {
        appRepository.fetchingMostPopMovies().collect {
            _mostPopMoviesList.value = it
        }
    }

    private val _mostPopShowsList =
        MutableStateFlow<Resource<List<MostPopTVShowsModel>>>(Resource.Loading())
    val mostPopShowsList: StateFlow<Resource<List<MostPopTVShowsModel>>> = _mostPopShowsList

    fun getMostPopShowsData() = viewModelScope.launch {
        appRepository.fetchingMostPopShows().collect {
            _mostPopShowsList.value = it
        }
    }

    private val _boxOffList =
        MutableStateFlow<Resource<List<BoxOfficeModel>>>(Resource.Loading())
    val boxOffList: StateFlow<Resource<List<BoxOfficeModel>>> = _boxOffList

    fun getBoxOfficeMoviesData() = viewModelScope.launch {
        appRepository.fetchingBoxOffice().collect {
            _boxOffList.value = it
        }
    }

}