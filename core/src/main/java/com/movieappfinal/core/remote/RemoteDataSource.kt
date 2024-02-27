package com.movieappfinal.core.remote

import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.services.ApiEndPoint
import com.movieappfinal.core.utils.safeApiCall

class RemoteDataSource(private val apiEndPoint: ApiEndPoint) {
    suspend fun fetchPopularProduct(): PopularMovieResponse {
        return safeApiCall { apiEndPoint.fetchPopularMovie() }
    }

    suspend fun fetchNowPlayingProduct(): NowPlayingResponse {
        return safeApiCall { apiEndPoint.fetchNowPlayingMovie() }
    }

    suspend fun fetchDetailMovie(
        id: Int? = 0
    ): DetailMovieResponse {
        return safeApiCall {
            apiEndPoint.fetchDetailMovie(
                id
            )
        }
    }


}