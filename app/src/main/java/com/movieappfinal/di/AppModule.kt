package com.movieappfinal.di

import com.movieappfinal.core.utils.BaseModules
import com.movieappfinal.viewmodel.AuthViewModel
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule: BaseModules {
    val viewModelModule = module {
        viewModel{AuthViewModel()}
        viewModel{HomeViewModel()}
    }

    override fun getModules(): List<Module> = listOf(viewModelModule)

}