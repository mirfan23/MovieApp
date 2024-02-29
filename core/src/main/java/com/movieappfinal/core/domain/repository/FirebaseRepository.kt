package com.movieappfinal.core.domain.repository

import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.movieappfinal.core.domain.model.DataTokenTransaction
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun signUpFirebase(email: String, password: String): Flow<Boolean>
    fun signInFirebase(email: String, password: String): Flow<Boolean>
    fun getCurrentUser(): FirebaseUser?
    fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean>
    fun logScreenView(screenName: String)
    fun logEvent(eventName: String, bundle: Bundle)
    fun getConfigStatusUpdate(): Flow<Boolean>
    fun getConfigPayment(): Flow<String>
    fun deleteAccount(): Flow<Boolean>
    fun getConfigStatusUpdatePayment(): Flow<Boolean>
    fun getConfigPaymentMethod(): Flow<String>
//    fun sendDataToDatabase(userName: String): Flow<Boolean>
}