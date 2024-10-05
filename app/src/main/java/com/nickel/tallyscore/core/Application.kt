package com.nickel.tallyscore.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TallyScoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.context = this.applicationContext
    }
}