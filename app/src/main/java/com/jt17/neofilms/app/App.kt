package com.jt17.neofilms.app

import android.app.Application
import com.jt17.neofilms.data.sharedPreferences.AppPreferences
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPreferences  .init(this)
        Hawk.init(this)

    }

}