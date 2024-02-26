package com.movieappfinal.core.utils

import com.movieappfinal.core.domain.model.DataCarousel
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataGenre
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataNowPlayingItem
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse

object DataMapper {
    fun PopularMovieResponse.Result.toUIPopularData() = DataPopularMovieItem(
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
        itemsPopular = results.map { item -> item.toUIPopularData() }
    )

    fun NowPlayingResponse.Result.toUINowPlayingData() = DataNowPlayingItem(
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

    fun DetailMovieResponse.Genre.toGenreData() = DataGenre(
        id = id,
        name = name
    )

    fun DetailMovieResponse.toUIData() = DataDetailMovie(
        id = id,
        backdrop = backdropPath,
        title = title,
        releaseDate = releaseDate,
        poster = posterPath,
        overview = overview,
        runtime = runtime,
        genres = genres.map { genre -> genre.toGenreData() },
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