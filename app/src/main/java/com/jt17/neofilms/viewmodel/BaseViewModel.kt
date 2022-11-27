package com.jt17.neofilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BaseViewModel: ViewModel() {
    val txt = MutableLiveData<String>()
}