package com.jt17.neofilms.data.sharedPreferences

import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.MostPopMoviesModel
import com.jt17.neofilms.models.MostPopTVShowsModel
import com.jt17.neofilms.models.TitleModel
import com.orhanobut.hawk.Hawk

object HawkPreferences {
    private const val BOX_OFFICE_ALL_TIME_KEY = "boxOfficeAllTime"

    //For in Theaters
    fun getBoxOfficeAllTimeHawk(): List<String> = Hawk.get(BOX_OFFICE_ALL_TIME_KEY, emptyList())

    fun setBoxOfficeAllTimeHawk(list: List<String>) = Hawk.put(BOX_OFFICE_ALL_TIME_KEY, list)

}