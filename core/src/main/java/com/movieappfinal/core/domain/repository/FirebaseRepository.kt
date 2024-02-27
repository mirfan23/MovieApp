package com.movieappfinal.core.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    fun signUpFirebase(email: String, password: String): Flow<Boolean>
    fun signInFirebase(email: String, password: String): Flow<Boolean>
    fun getCurrentUser(): FirebaseUser?
    fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean>
}