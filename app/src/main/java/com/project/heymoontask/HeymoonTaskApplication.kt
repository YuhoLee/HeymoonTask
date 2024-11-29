package com.project.heymoontask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeymoonTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}