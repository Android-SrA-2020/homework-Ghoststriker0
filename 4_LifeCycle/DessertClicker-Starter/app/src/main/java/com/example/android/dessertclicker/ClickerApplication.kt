package com.example.android.dessertclicker

import android.app.Application
import timber.log.Timber

/**
 * Created by Martin Mallet on 2020-02-01
 */
class ClickerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }


}