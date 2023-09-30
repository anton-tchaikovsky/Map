package com.gb.map

import android.app.Application
import com.gb.map.koin.applicationModule
import com.gb.map.koin.activityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MapApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MapApp)
            modules(listOf(applicationModule, activityModule))
        }
    }
}