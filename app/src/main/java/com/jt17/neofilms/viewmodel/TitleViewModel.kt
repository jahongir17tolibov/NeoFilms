package com.jt17.neofilms.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jt17.neofilms.models.*
import com.jt17.neofilms.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _title = MutableStateFlow<Resource<TitleModel>>(Resource.Loading())
    val title: StateFlow<Resource<TitleModel>>
        get() = _title

    fun getAllFilmsInfo(id: String) = viewModelScope.launch {
        appRepository.fetchingAllFilmsData(id).collect {
            Log.d("jt1771tj", "getAllFilmsInfo: $it")
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

    fun insertFavMovie(favMoviesModel: FavMoviesModel) = viewModelScope.launch {
        appRepository.insertFavMovie(favMoviesModel)
    }

    fun getFavMovieID(id: String?): LiveData<FavMoviesModel> =
        appRepository.getFavMovieID(id).asLiveData()

    fun insertFavSeries(favSeriesModel: FavSeriesModel) = viewModelScope.launch {
        appRepository.insertFavSeries(favSeriesModel)
    }

    fun getFavSeriesID(id: String?): LiveData<FavSeriesModel> =
        appRepository.getFavSeriesID(id).asLiveData()

}