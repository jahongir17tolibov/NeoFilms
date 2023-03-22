package com.jt17.neofilms.utils

import android.content.Context
import android.widget.Toast
import com.jt17.neofilms.models.*
import com.orhanobut.hawk.Hawk

object BaseUtils {
    private const val API_LIST_KEY = "apiListKey"
    private const val API_CS_KEY = "ComingSoonKey"
    private const val API_MP_KEY = "MostPopKey"

    //For in Theaters Hawk
    fun getApiList(): List<InTheatresModel> {
        return Hawk.get(API_LIST_KEY, emptyList())
    }

    fun setApiList(list: List<InTheatresModel>) {
        Hawk.put(API_LIST_KEY, list)
    }


    //For Coming soon movies
    fun getApiCS(): List<ComingSoonModel> {
        return Hawk.get(API_CS_KEY, emptyList())
    }

    fun setApiCS(list: List<ComingSoonModel>) {
        Hawk.put(API_CS_KEY, list)
    }

    //For Most popular films Hawk
    fun getApiMP(): List<MostPopMoviesModel> {
        return Hawk.get(API_MP_KEY, emptyList())
    }

    fun setApiMP(list: List<MostPopMoviesModel>) {
        Hawk.put(API_MP_KEY, list)
    }

}