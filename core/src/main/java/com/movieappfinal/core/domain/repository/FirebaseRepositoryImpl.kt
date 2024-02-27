package com.movieappfinal.core.domain.repository

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl (
    private val firebaseAnalytics: FirebaseAnalytics,
    private val remoteConfig: FirebaseRemoteConfig,
    private val auth: FirebaseAuth
) : FirebaseRepository{
    override fun signUpFirebase(email: String, password: String): Flow<Boolean> = callbackFlow{
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            trySend(task.isSuccessful)
            if (task.isSuccessful) {
                println("MASUK: createUserWithEmail: success")
            } else {
                println("MASUK: createUserWithEmail: failed ${task.exception}")

            }
        }
        awaitClose()
    }

    override fun signInFirebase(email: String, password: String): Flow<Boolean> = callbackFlow{
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            trySend(task.isSuccessful)
            if (task.isSuccessful) {
                println("MASUK: signInUserWithEmail: success")
            } else {
                println("MASUK: signInUserWithEmail: failed ${task.exception}")

            }
        }
        awaitClose()
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> = callbackFlow{
        getCurrentUser()?.updateProfile(userProfileChangeRequest)?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                trySend(task.isSuccessful)
                println("MASUK: signInUserWithEmail: success")
            } else {
                println("MASUK: signInUserWithEmail: failed ${task.exception}")

            }
        }
        awaitClose()
    }


}