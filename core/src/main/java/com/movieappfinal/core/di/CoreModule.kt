package com.movieappfinal.core.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.movieappfinal.core.local.preferences.SharedPreferencesHelper
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.repository.FirebaseRepositoryImpl
import com.movieappfinal.core.domain.repository.MovieRepository
import com.movieappfinal.core.domain.repository.MovieRepositoryImpl
import com.movieappfinal.core.domain.usecase.AppInteractor
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.local.LocalDataSource
import com.movieappfinal.core.local.database.MovieDatabase
import com.movieappfinal.core.local.preferences.SharedPreferenceImpl
import com.movieappfinal.core.local.preferences.SharedPreferenceImpl.Companion.PREFS_NAME
import com.movieappfinal.core.remote.PagingDataSource
import com.movieappfinal.core.remote.RemoteDataSource
import com.movieappfinal.core.remote.client.MovieClient
import com.movieappfinal.core.remote.interceptor.MovieInterceptor
import com.movieappfinal.core.remote.services.ApiEndPoint
import com.movieappfinal.core.utils.BaseModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule : BaseModules {
    val sharedPrefModule = module {
        single { androidContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
        single<SharedPreferencesHelper> {
            SharedPreferenceImpl(get())
        }
    }
    val dataSourceModule = module {
        single { RemoteDataSource(get()) }
        single { LocalDataSource(get(), get()) }
    }
    val networkModule = module {
        single {
            ChuckerInterceptor.Builder(androidContext()).redactHeaders("Authorization", "Bearer")
                .build()
        }
        single { MovieInterceptor() }
        single { MovieClient(get(), get()) }
        single<ApiEndPoint> { get<MovieClient>().create() }
    }

    val movieRepositoryModule = module {
        single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    }
    val useCaseModule = module {
        single<AppUseCase> { AppInteractor(get(), get(), get()) }
    }
    val firebaseModule = module {
        single { Firebase.analytics }
        single { Firebase.remoteConfig }
        single { Firebase.auth }
        single { Firebase.database }
        single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get(), get(), get( )) }
    }
    val databaseModule = module {
        single {
            Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<MovieDatabase>().movieDao() }
    }

    override fun getModules(): List<Module> = listOf(
        sharedPrefModule,
        dataSourceModule,
        useCaseModule,
        networkModule,
        firebaseModule,
        movieRepositoryModule,
        databaseModule
    )
}