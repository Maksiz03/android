package com.example.myapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin with the appModule
        startKoin {
            // Provide the Android context
            androidContext(this@MyApplication)
            // Load the module
            modules(AppModule)
        }
    }
}
