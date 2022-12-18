package com.jt17.neofilms.db

import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.orhanobut.hawk.Hawk
import java.lang.reflect.Array.set

object BaseUtils {
    private const val API_LIST_KEY = "apiListKey"
    private const val API_MP_KEY = "MostPopKey"

    //For in Theaters Hawk
    fun getApiList(): List<InTheatresModel> {
        return Hawk.get(API_LIST_KEY, emptyList())
    }
    fun setApiList(list: List<InTheatresModel>) {
        Hawk.put(API_LIST_KEY, list)
    }


    //For Most popular films Hawk
    fun getApiMP(): List<mainModel> {
        return Hawk.get(API_MP_KEY, emptyList())
    }

    fun setApiMP(list: List<mainModel>) {
        Hawk.put(API_MP_KEY, list)
    }

}