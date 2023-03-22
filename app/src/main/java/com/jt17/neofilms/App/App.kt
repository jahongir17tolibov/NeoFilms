package com.jt17.neofilms.App

import android.app.Application
import com.jt17.neofilms.data.sharedPreferences.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPreferences.init(this)
    }

}