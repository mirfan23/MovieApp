package com.movieappfinal.core.remote.services

import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.data.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndPoint {
    @GET("movie/popular?page=2")
    suspend fun fetchPopularMovie(): PopularMovieResponse

    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovie(): NowPlayingResponse

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovie(): TrendingMovieResponse

    @GET("movie/{movie_id}")
    suspend fun fetchDetailMovie(
        @Path("movie_id") movieId: Int? = 0
    ): DetailMovieResponse
}