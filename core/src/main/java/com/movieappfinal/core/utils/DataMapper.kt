package com.movieappfinal.core.utils

import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataNowPlayingItem
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse

object DataMapper {
    fun PopularMovieResponse.Result.toUIPopularData() = DataPopularMovieItem (
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate
    )

    fun PopularMovieResponse.toUIData() = DataPopularMovie(
        title = "",
        items = results.map { item -> item.toUIPopularData() }
    )

    fun NowPlayingResponse.Result.toUINowPlayingData() = DataNowPlayingItem (
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate
    )

    fun NowPlayingResponse.toUIData() = DataNowPlaying(
        title = "",
        items = results.map { item -> item.toUINowPlayingData() }
    )

    fun Triple<String, String, Boolean>.toUIData() = DataSession(
        name = this.first,
        uid = this.second,
        onBoardingState = this.third
    )

    fun DataSession.toSplashState() = when {
        this.name.isEmpty() && this.uid.isNullOrEmpty().not() -> {
            SplashState.Profile
        }

        this.name.isNullOrEmpty().not() && this.uid.isNullOrEmpty().not() -> {
            SplashState.Dashboard
        }

        this.onBoardingState -> {
            SplashState.Login
        }

        else -> {
            SplashState.OnBoarding
        }
    }
}