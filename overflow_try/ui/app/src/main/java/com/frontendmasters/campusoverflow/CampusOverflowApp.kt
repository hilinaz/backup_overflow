package com.frontendmasters.campusoverflow

import android.app.Application
import com.frontendmasters.campusoverflow.api.ApiClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CampusOverflowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.initialize(this)
    }
} 