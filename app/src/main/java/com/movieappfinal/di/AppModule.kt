package com.movieappfinal.di

import com.movieappfinal.core.utils.BaseModules
import com.movieappfinal.viewmodel.AuthViewModel
import com.movieappfinal.viewmodel.DashboardViewModel
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule : BaseModules {
    private val viewModelModule = module {
        viewModel { AuthViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get()) }
        viewModel { DashboardViewModel(get(), get()) }
    }

    override fun getModules(): List<Module> = listOf(viewModelModule)

}