package com.movieappfinal.core.remote.services

import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import retrofit2.http.GET

interface ApiEndPoint {
    @GET("movie/popular")
    suspend fun fetchPopularMovie(): PopularMovieResponse
    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovie(): NowPlayingResponse
}