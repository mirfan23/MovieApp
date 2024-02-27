package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.utils.DataMapper.toSplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class DashboardViewModel(private val useCase: AppUseCase): ViewModel() {

    private  val _onBoarding = MutableStateFlow<SplashState<DataSession>>(SplashState.OnBoarding)
    val onBoarding = _onBoarding.asStateFlow()

    private val _uid = MutableStateFlow("")
    val uid = _uid.asStateFlow()


    fun putOnBoardingState(value: Boolean) {
        useCase.saveOnBoardingState(value)
    }

    fun getOnBoardingState(){
        _onBoarding.update { useCase.dataSession().toSplashState() }
    }

    fun getCurrentUser() = runBlocking { useCase.getCurrentUser() }

    fun getUID() {
        _uid.update { useCase.getUid() }
    }

    fun putUID(value: String) {
        useCase.putUid(value)
    }
}