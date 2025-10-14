package com.roadrunner.roadmate

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoadMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"RoadMate: Application class called")
    }

    companion object {
        const val TAG = "RoadMateApplication"
    }
}