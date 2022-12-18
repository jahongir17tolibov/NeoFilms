package com.jt17.neofilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel

class BaseViewModel: ViewModel() {
    //    val txt = MutableLiveData<String>()
    val netList = MutableLiveData<List<mainModel>>()

    //    val metlist = MutableLiveData<List<mainModel>>()
    val nestedList = MutableLiveData<List<InTheatresModel>>()
}