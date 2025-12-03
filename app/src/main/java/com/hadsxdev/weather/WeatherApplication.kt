package com.hadsxdev.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide components here
    }
}