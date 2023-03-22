package com.jt17.neofilms.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

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

}