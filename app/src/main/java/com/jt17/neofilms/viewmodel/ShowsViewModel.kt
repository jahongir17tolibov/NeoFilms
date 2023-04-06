package com.jt17.neofilms.viewmodel

import androidx.lifecycle.*
import com.jt17.neofilms.models.*
import com.jt17.neofilms.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _topShowsList =
        MutableStateFlow<Resource<List<Top250ShowsModel>>>(Resource.Loading())
    val topShowsList: StateFlow<Resource<List<Top250ShowsModel>>> = _topShowsList

    fun getTopShowsData() = viewModelScope.launch {
        appRepository.fetchingTopShows().collect {
            _topShowsList.value = it
        }
    }

    private val _mostPopShowsImageList = MutableLiveData<List<String>>()
    val mostPopShowsImageList: LiveData<List<String>> = _mostPopShowsImageList

    fun getMostPopShowsImg() = viewModelScope.launch {
        appRepository.fetchMostPopShowsImg().collect {
            _mostPopShowsImageList.value = it
        }
    }

    private val _wikiData = MutableLiveData<Resource<WikiModel>>()
    val wikiData: LiveData<Resource<WikiModel>> = _wikiData

    fun getFilmsWiki(id: String) = viewModelScope.launch {
        appRepository.fetchingFilmsWikiData(id).collect {
            _wikiData.value = it
        }
    }

    private val _seasonEpsData = MutableStateFlow<Resource<SeasonModel>>(Resource.Loading())
    val seasonEpsData: StateFlow<Resource<SeasonModel>> = _seasonEpsData

    fun getSeasonEps(id: String, seasonNumb: String) = viewModelScope.launch {
        appRepository.fetchingSeasonEpsData(id, seasonNumb).collect {
            _seasonEpsData.value = it
        }
    }

    //caching all most popular TV shows movies data from repository
    val getAllMostPopShowsCachedList: LiveData<List<MostPopTVShowsModel>> =
        appRepository.getAllMostPopShowsData().asLiveData()

    fun insertFavSeries(favSeriesModel: FavSeriesModel) = viewModelScope.launch {
        appRepository.insertFavSeries(favSeriesModel)
    }

    val getAllFavSeries: LiveData<List<FavSeriesModel>> =
        appRepository.getAllFavSeriesData().asLiveData()

    fun clearAllFavSeries() = viewModelScope.launch {
        appRepository.clearAllFavSeries()
    }

    fun deleteOneFavSeries(favSeriesModel: FavSeriesModel) = viewModelScope.launch {
        appRepository.deleteOneFavShow(favSeriesModel)
    }

    fun getFavMovieID(id: String?): LiveData<FavSeriesModel> =
        appRepository.getFavSeriesID(id).asLiveData()

}