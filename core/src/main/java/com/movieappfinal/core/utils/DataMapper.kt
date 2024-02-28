package com.movieappfinal.core.utils

import com.example.core.domain.model.DataPaymentMethod
import com.example.core.domain.model.DataPaymentMethodItem
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataGenre
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataNowPlayingItem
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataTrendingMovieItem
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity
import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.MoviesItem
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PaymentMethodResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.data.TokenPaymentResponse
import com.movieappfinal.core.remote.data.TrendingMovieResponse

object DataMapper {
    private fun MoviesItem.toUIPopularData() = DataPopularMovieItem(
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate,
        popularity = popularity.toInt()
    )

    fun PopularMovieResponse.toUIData() = DataPopularMovie(
        title = "",
        itemsPopular = results.map { item -> item.toUIPopularData() }
    )

    private fun MoviesItem.toUINowPlayingData() = DataNowPlayingItem(
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate,
        popularity = popularity.toInt()
    )

    fun NowPlayingResponse.toUIData() = DataNowPlaying(
        title = "",
        items = results.map { item -> item.toUINowPlayingData() }
    )

    private fun MoviesItem.toUITrendingData() = DataTrendingMovieItem(
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate,
        popularity = popularity.toInt()
    )

    fun TrendingMovieResponse.toUIData() = DataTrendingMovie(
        title = "",
        items = results.map { item -> item.toUITrendingData() }
    )

    private fun DetailMovieResponse.Genre.toGenreData() = DataGenre(
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
        popularity = popularity.toInt()
    )

    fun CartEntity.toUIData() = DataCart(
        cartId = cartId,
        movieId = movieId,
        image = image,
        movieTitle = movieTitle,
        moviePrice = moviePrice,
        userId = userId,
        isChecked = isChecked,
    )

    fun DataCart.toEntity() = CartEntity(
        cartId = cartId,
        movieId = movieId,
        image = image,
        movieTitle = movieTitle,
        moviePrice = moviePrice,
        userId = userId,
        isChecked = isChecked,
    )

    fun WishListEntity.toUIData() = DataWishlist(
        wishlistId = wishlistId,
        movieId = movieId,
        image = image,
        movieTitle = movieTitle,
        moviePrice = moviePrice,
        userId = userId,
        releaseDate = releaseDate,
        wishlist = wishlist,
    )

    fun DataWishlist.toEntity() = WishListEntity(
        wishlistId = wishlistId,
        movieId = movieId,
        image = image,
        movieTitle = movieTitle,
        moviePrice = moviePrice,
        userId = userId,
        releaseDate = releaseDate,
        wishlist = wishlist,
    )

    fun MoviesItem.toSearchData() = DataSearchMovie(
        id = id,
        genreIds = genreIds,
        title = title,
        backdrop = backdropPath,
        overview = overview,
        poster = posterPath,
        releaseDate = releaseDate,
        popularity = popularity.toInt()
    )

    fun TokenPaymentResponse.TokenPaymentResponseItem.toUiData() = DataTokenPaymentItem(
        token = token,
        price = price
    )

    fun PaymentMethodResponse.DataPayment.toUIData() = DataPaymentMethod(
        item = item.map { item -> item.toUIData() },
        title = title
    )

    fun PaymentMethodResponse.DataPayment.Item.toUIData() = DataPaymentMethodItem(
        image = image,
        label = label,
        status = status
    )

    fun Triple<String, String, Boolean>.toUIData() = DataSession(
        name = this.first,
        uid = this.second,
        onBoardingState = this.third
    )

    fun DataSession.toSplashState() = when {
        this.name?.isEmpty() == true -> {
            SplashState.Profile
        }
        this.name?.isNotEmpty() == true  -> {
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