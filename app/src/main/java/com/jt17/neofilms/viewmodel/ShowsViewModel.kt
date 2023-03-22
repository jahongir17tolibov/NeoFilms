package com.jt17.neofilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.models.Top250MoviesModel
import com.jt17.neofilms.models.Top250ShowsModel
import com.jt17.neofilms.models.TopShowsInception
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

}