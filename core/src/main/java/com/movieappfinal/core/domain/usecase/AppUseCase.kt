package com.movieappfinal.core.domain.usecase

import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataSession

interface AppUseCase {

    suspend fun fetchPopularMovie(): DataPopularMovie
    suspend fun fetchNowPlayingMovie(): DataNowPlaying
    fun dataSession(): DataSession
    fun saveOnBoardingState(value: Boolean)
    fun getOnBoardingState(): Boolean
    fun putWishlistState(value: Boolean)
    fun getWishlistState(): Boolean
    fun putThemeStatus(value: Boolean)
    fun getThemeStatus(): Boolean
    fun putLanguageStatus(value: String)
    fun saveProfileName(string: String)
    fun getProfileName(): String
    fun getLanguageStatus(): String
    fun putUid(value: String)
    fun getUid(): String?
    fun clearAllSession()
}