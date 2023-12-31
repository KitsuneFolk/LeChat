package com.pandacorp.togetheraichat.di.app

import android.app.Application
import com.pandacorp.togetheraichat.di.modules.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        // Throw uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler { _, throwable -> throw (throwable) }
        super.onCreate()
        instance = this

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)

            modules(
                listOf(koinModule)
            )
        }
    }

    companion object {
        lateinit var instance: App
    }
}