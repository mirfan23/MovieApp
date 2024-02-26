package com.movieappfinal.core.domain.repository

import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse

interface MovieRepository {
    suspend fun fetchPopularMovie(): PopularMovieResponse
    suspend fun fetchNowPlayingMovie(): NowPlayingResponse
    suspend fun dataSession(name: String, accessToken: String, onBoardingState: Boolean)
    fun getProfileName(): String
    fun saveProfileName(string: String)
    fun getOnBoardingState(): Boolean
    fun saveOnBoardingState(state: Boolean)
    fun getUid(): String
    fun saveUid(string: String)
}