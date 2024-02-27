package com.movieappfinal.core.domain.usecase

import com.example.core.local.preferences.SharedPreferencesHelper
import com.google.firebase.auth.UserProfileChangeRequest
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataProfile
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.repository.MovieRepository
import com.movieappfinal.core.utils.DataMapper.toUIData
import com.movieappfinal.core.utils.safeDataCall
import kotlinx.coroutines.flow.Flow

class AppInteractor(
    private val movieRepo: MovieRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val firebaseRepo: FirebaseRepository
) : AppUseCase {
    override suspend fun fetchPopularMovie(): DataPopularMovie = safeDataCall {
        movieRepo.fetchPopularMovie().toUIData()
    }

    override suspend fun fetchNowPlayingMovie(): DataNowPlaying = safeDataCall {
        movieRepo.fetchNowPlayingMovie().toUIData()
    }

    override suspend fun fetchDetailMovie(movieId: Int): DataDetailMovie = safeDataCall {
        movieRepo.fetchDetailMovie(movieId).toUIData()
    }

    override suspend fun signUpFirebase(email: String, password: String): Flow<Boolean> = safeDataCall{
        firebaseRepo.signUpFirebase(email, password)
    }

    override suspend fun signInFirebase(email: String, password: String): Flow<Boolean> = safeDataCall {
        firebaseRepo.signInFirebase(email, password)
    }

    override suspend fun getCurrentUser(): DataProfile? {
        return  firebaseRepo.getCurrentUser()?.displayName?.let { DataProfile(it) }
    }

    override suspend fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> = safeDataCall {
        firebaseRepo.updateProfile(userProfileChangeRequest)
    }

    override fun dataSession(): DataSession {
        val name = movieRepo.getProfileName()
        val uid = movieRepo.getUid()
        val onBoardingState = movieRepo.getOnBoardingState()
        val triple: Triple<String, String, Boolean> = Triple(name, uid, onBoardingState)
        return triple.toUIData()
    }

    override fun saveOnBoardingState(value: Boolean) {
        movieRepo.saveOnBoardingState(value)
    }

    override fun getOnBoardingState(): Boolean = movieRepo.getOnBoardingState()
    override fun putWishlistState(value: Boolean) {
//         productRepo.putWishlistState(value)
    }

    override fun getWishlistState(): Boolean {
        TODO()
    }
//     = productRepo.getWishlistState()


    override fun putThemeStatus(value: Boolean) {
        sharedPreferencesHelper.putThemeStatus(value)
    }

    override fun getThemeStatus(): Boolean = sharedPreferencesHelper.getThemeStatus()

    override fun putLanguageStatus(value: String) {
        sharedPreferencesHelper.putLanguageStatus(value)
    }

    override fun getLanguageStatus(): String = sharedPreferencesHelper.getLanguageStatus()

    override fun clearAllSession() {
        sharedPreferencesHelper.clearAllSession()
    }

    override fun putUid(value: String) {
        sharedPreferencesHelper.putUid(value)
    }

    override fun getUid(): String = sharedPreferencesHelper.getUid()

    override fun saveProfileName(string: String) {
        movieRepo.saveProfileName(string)
    }

    override fun getProfileName(): String = movieRepo.getProfileName()
}