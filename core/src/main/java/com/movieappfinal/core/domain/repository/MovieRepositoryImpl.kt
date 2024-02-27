package com.movieappfinal.core.domain.repository

import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.local.LocalDataSource
import com.movieappfinal.core.remote.RemoteDataSource
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.utils.safeDataCall

class MovieRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : MovieRepository{
    override suspend fun fetchPopularMovie(): PopularMovieResponse = safeDataCall {
        remote.fetchPopularProduct()
    }

    override suspend fun fetchNowPlayingMovie(): NowPlayingResponse = safeDataCall {
        remote.fetchNowPlayingProduct()
    }
    override suspend fun dataSession(name: String, accessToken: String, onBoardingState: Boolean) {}
    override suspend fun fetchDetailMovie(movieId: Int?): DetailMovieResponse = safeDataCall {
        remote.fetchDetailMovie(movieId)
    }

    override fun getProfileName(): String = local.getProfileName()

    override fun saveProfileName(string: String) {
        local.saveProfileName(string)
    }

    override fun getOnBoardingState(): Boolean = local.getOnBoardingState()

    override fun saveOnBoardingState(state: Boolean) {
        local.saveOnBoardingState(state)
    }

    override fun getUid(): String = local.getUid()

    override fun saveUid(string: String)  {
        local.putUid(string)
    }

}