package com.movieappfinal.core.domain.repository

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.movieappfinal.core.domain.model.DataTokenTransaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val remoteConfig: FirebaseRemoteConfig,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : FirebaseRepository {
    override fun signUpFirebase(email: String, password: String): Flow<Boolean> = callbackFlow {
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

    override fun signInFirebase(email: String, password: String): Flow<Boolean> = callbackFlow {
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

    override fun deleteAccount(): Flow<Boolean> = callbackFlow{
        auth.currentUser?.delete()?.addOnCompleteListener { task ->
            trySend(task.isSuccessful)
            if (task.isSuccessful){
                println("MASUK: deleteUser: success")
            } else {
                println("MASUK: deleteUser: failed ${task.exception}")

            }
        }
        awaitClose()
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser
    override fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> =
        callbackFlow {
            getCurrentUser()?.updateProfile(userProfileChangeRequest)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(task.isSuccessful)
                        println("MASUK: signInUserWithEmail: success")
                    } else {
                        println("MASUK: signInUserWithEmail: failed ${task.exception}")

                    }
                }
            awaitClose()
        }

    override fun logScreenView(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override fun logEvent(eventName: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun getConfigStatusUpdate(): Flow<Boolean> =
        callbackFlow {
            remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    if (configUpdate.updatedKeys.contains("token_payment")) {
                        remoteConfig.activate().addOnCompleteListener { task ->
                            trySend(task.isSuccessful)
                        }
                    }
                }

                override fun onError(error: FirebaseRemoteConfigException) {
                    Log.d("MASUK : ", "Dynamic Remote Config Error: $error")
                    trySend(false)
                }
            })
            awaitClose()
        }

    override fun getConfigPayment(): Flow<String> = callbackFlow {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            trySend(remoteConfig.getString("token_payment"))
        }
        awaitClose()
    }

    override fun getConfigStatusUpdatePayment(): Flow<Boolean> =
        callbackFlow {
            remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    if (configUpdate.updatedKeys.contains("payment_method")) {
                        remoteConfig.activate().addOnCompleteListener { task ->
                            trySend(task.isSuccessful)
                        }
                    }
                }

                override fun onError(error: FirebaseRemoteConfigException) {
                    Log.d("MASUK : ", "Dynamic Remote Config Error: $error")
                    trySend(false)
                }
            })
            awaitClose()
        }

    override fun getConfigPaymentMethod(): Flow<String> = callbackFlow {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            trySend(remoteConfig.getString("payment_method"))
        }
        awaitClose()
    }

//    override fun sendDataToDatabase(userName: String): Flow<Boolean> = callbackFlow {
//        database.getReference("token_transaction").child(userName).push().addValueEventListener(
//
//        )
//    }

}