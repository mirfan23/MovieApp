package com.movieappfinal

import android.app.Application
import com.movieappfinal.core.di.CoreModule
import com.movieappfinal.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.NONE)
            androidContext(this@MovieApplication)
            /**
             *commented code will be use later
             */
            modules(
                AppModule.getModules()
            )
            modules(
                CoreModule.getModules()
            )
//            modules(
//                ProductModule.getModules()
//            )
        }
    }
}