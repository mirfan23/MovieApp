package com.movieappfinal.core.domain.repository

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.domain.state.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val remoteConfig: FirebaseRemoteConfig,
    private val auth: FirebaseAuth,
    private val database: DatabaseReference
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

    override fun deleteAccount(): Flow<Boolean> = callbackFlow {
        auth.currentUser?.delete()?.addOnCompleteListener { task ->
            trySend(task.isSuccessful)
            if (task.isSuccessful) {
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

    override suspend fun sendDataToDatabase(
        dataTokenTransaction: DataTokenTransaction,
        userId: String
    ): Flow<Boolean> = callbackFlow {
        trySend(false)
        database.database.reference.child("token_transaction").child(userId).push()
            .setValue(dataTokenTransaction)
            .addOnCompleteListener { task ->
                trySend(task.isSuccessful)
            }.addOnFailureListener { e ->
                trySend(e.message?.isNotEmpty() ?: false)
            }
        awaitClose()
    }

    override suspend fun sendMovieToDataBase(
        dataMovieTransaction: DataMovieTransaction,
        userId: String
    ): Flow<Boolean> = callbackFlow {
        trySend(false)
        database.database.reference.child("movie_transaction").child(userId).push()
            .setValue(dataMovieTransaction)
            .addOnCompleteListener { task ->
                trySend(task.isSuccessful)
            }.addOnFailureListener { e ->
                trySend(e.message?.isNotEmpty() ?: false)
            }
        awaitClose()
    }

    override suspend fun getTokenFromFirebase(userId: String): Flow<Int> = callbackFlow {
        trySend(0)
        database.database.reference.child("token_transaction").child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalToken = 0
                    for (transaction in snapshot.children) {
                        val token = transaction.child("tokenAmount").getValue(String::class.java)
                        val tokenAmount = token?.toIntOrNull() ?: 0
                        totalToken += tokenAmount
                    }
                    trySend(totalToken)
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(0)
                }
            })
        awaitClose()
    }

    override suspend fun getMovieTransactionFromFirebase(userId: String): Flow<Int> = callbackFlow {
        trySend(0)
        database.database.reference.child("movie_transaction").child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalPrice = 0

                    for (transaction in snapshot.children) {
                        val price = transaction.child("totalPrice").getValue(Int::class.java)
                        if (price != null) {
                            totalPrice += price
                        }
                    }
                    trySend(totalPrice)
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(0)
                }
            })
        awaitClose()
    }

}