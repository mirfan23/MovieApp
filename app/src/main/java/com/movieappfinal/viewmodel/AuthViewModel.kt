package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.UserProfileChangeRequest
import com.movieappfinal.core.domain.model.DataProfile
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.state.FlowState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.utils.validateEmail
import com.movieappfinal.utils.validatePassword
import com.movieappfinal.utils.validateRequired
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel(private val useCase: AppUseCase, private val fireRepo: FirebaseRepository) :
    ViewModel() {

    private val _validateLoginEmail: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginEmail = _validateLoginEmail.asStateFlow()

    private val _validateLoginPassword: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginPassword = _validateLoginPassword.asStateFlow()

    private val _validateRegisterEmail: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterEmail = _validateRegisterEmail.asStateFlow()

    private val _validateRegisterPassword: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterPassword = _validateRegisterPassword.asStateFlow()

    private val _validateProfileName: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateProfileName = _validateProfileName.asStateFlow()

    private val _validateLoginField: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginField = _validateLoginField.asSharedFlow()

    private val _validateRegisterField: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterField = _validateRegisterField.asSharedFlow()

    private val _validateProfileField: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateProfileField = _validateProfileField.asSharedFlow()


    fun signUpWithFirebase(email: String, password: String) =
        runBlocking { useCase.signUpFirebase(email, password) }

    fun signInWithFirebase(email: String, password: String) =
        runBlocking { useCase.signInFirebase(email, password) }

    fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest) =
        runBlocking { useCase.updateProfile(userProfileChangeRequest) }


    fun validateLoginEmail(email: String) {
        _validateLoginEmail.update { FlowState.FlowValue(email.validateEmail()) }
    }

    fun validateLoginPassword(password: String) {
        _validateLoginPassword.update { FlowState.FlowValue(password.validatePassword()) }
    }

    fun validateRegisterEmail(email: String) {
        _validateRegisterEmail.update { FlowState.FlowValue(email.validateEmail()) }
    }

    fun validateRegisterPassword(password: String) {
        _validateRegisterPassword.update { FlowState.FlowValue(password.validatePassword()) }
    }

    fun validateProfileName(name: String) {
        _validateProfileName.update { FlowState.FlowValue(name.validateRequired()) }
    }

    fun validateLoginField(email: String, password: String) {
        _validateLoginField.update { FlowState.FlowValue(email.validateRequired() && password.validateRequired()) }
    }

    fun validateRegisterField(email: String, password: String) {
        _validateRegisterField.update { FlowState.FlowValue(email.validateRequired() && password.validateRequired()) }
    }

    fun resetValidateLoginField() {
        _validateLoginField.update { FlowState.FlowCreated }
    }

    fun resetValidateRegisterField() {
        _validateLoginField.update { FlowState.FlowCreated }
    }

    fun firebaseAnalytic(screenName: String) {
        viewModelScope.launch {
            fireRepo.logScreenView(screenName)
        }
    }
}
