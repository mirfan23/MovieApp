package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.utils.DataMapper.toSplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    private val useCase: AppUseCase,
    private val fireRepo: FirebaseRepository
) : ViewModel() {

    private val _onBoarding = MutableStateFlow<SplashState<DataSession>>(SplashState.OnBoarding)
    val onBoarding = _onBoarding.asStateFlow()

    private val _uid = MutableStateFlow("")
    val uid = _uid.asStateFlow()

    val selectedItem = MutableStateFlow<DataTokenPaymentItem?>(null)

    fun setSelectedItem(item: DataTokenPaymentItem) {
        selectedItem.value = item
    }

    fun putOnBoardingState(value: Boolean) {
        useCase.saveOnBoardingState(value)
    }

    fun getOnBoardingState() {
        _onBoarding.update { useCase.dataSession().toSplashState() }
    }

    fun getCurrentUser() = runBlocking { useCase.getCurrentUser() }

    fun getUID() {
        _uid.update { useCase.getUid() }
    }

    fun putUID(value: String) {
        useCase.putUid(value)
    }

    fun saveProfileName(value: String) {
        useCase.saveProfileName(value)
    }

    fun doPayment() = runBlocking {
        useCase.getConfigPayment()
    }

    fun getConfigStatusUpdate() = runBlocking { useCase.getConfigStatusUpdate() }

    fun doPaymentMethod() = runBlocking {
        useCase.getConfigPaymentMethod()
    }

    fun getConfigStatusUpdatePayment() = runBlocking { useCase.getConfigStatusUpdatePayment() }

    fun clearAllSession() {
        useCase.clearAllSession()
    }

    fun firebaseAnalytic(screenName: String) {
        viewModelScope.launch {
            fireRepo.logScreenView(screenName)
        }
    }

    fun sendMovieToDatabase(dataMovieTransaction: DataMovieTransaction, userId: String) =
        runBlocking {
            useCase.sendMovieToDatabase(dataMovieTransaction, userId)
        }

    fun getTokenFromDatabase(userId: String) = runBlocking { useCase.getTokenFromDatabase(userId) }

    fun getMovieTransactionFromDatabase(userId: String) =
        runBlocking { useCase.getMovieTransactionFromDatabase(userId) }

}
