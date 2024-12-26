package com.alpenraum.shimstack

import android.app.Application
import android.content.Context

class ShimstackApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}