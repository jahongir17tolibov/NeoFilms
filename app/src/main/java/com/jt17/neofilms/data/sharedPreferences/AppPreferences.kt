package com.jt17.neofilms.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.jt17.neofilms.models.InTheatresModel

class AppPreferences {

    companion object {
        private lateinit var sharedPref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private var shp: AppPreferences? = null

        fun init(context: Context) {
            shp = AppPreferences()
            sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            editor = sharedPref.edit()
        }

        fun getInstance() = shp!!

    }

    fun setNightModeState(state: Boolean) {
        editor.putBoolean("isNight", state)
        editor.apply()
    }

    fun loadNightModeState(): Boolean = sharedPref.getBoolean("isNight", false)

    fun setImageUrl(str: String) = editor.apply {
        putString("image", str)
        apply()
    }

    fun getImageUrl(): String = sharedPref.getString("image", "not found") ?: "api key problem"

    fun setImdbRating(str: String) = editor.apply {
        putString("image", str)
        apply()
    }

    fun getImdbRating(): String = sharedPref.getString("image", "not found") ?: "api key problem"

}