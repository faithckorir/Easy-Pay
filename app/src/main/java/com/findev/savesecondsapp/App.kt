package com.findev.savesecondsapp

import android.app.Application
import com.hover.sdk.api.Hover

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Required initialization logic here!
        Hover.initialize(applicationContext);
    }
}