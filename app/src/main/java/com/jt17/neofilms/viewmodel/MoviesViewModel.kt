package com.jt17.neofilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.models.Top250MoviesModel
import com.jt17.neofilms.repository.AppRepository
import com.jt17.neofilms.models.TopMoviesInception
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _topMoviesList =
        MutableStateFlow<Resource<List<Top250MoviesModel>>>(Resource.Loading())
    val topMoviesList: StateFlow<Resource<List<Top250MoviesModel>>> = _topMoviesList

    fun getTopMoviesData() = viewModelScope.launch {
        appRepository.fetchingTopMovies().collect {
            _topMoviesList.value = it
        }
    }

}