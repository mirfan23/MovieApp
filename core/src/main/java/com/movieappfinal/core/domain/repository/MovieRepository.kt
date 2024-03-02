package com.movieappfinal.core.domain.repository

import androidx.paging.PagingData
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity
import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.data.TrendingMovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchPopularMovie(): PopularMovieResponse
    suspend fun fetchNowPlayingMovie(): NowPlayingResponse
    suspend fun fetchTrendingMovie(): TrendingMovieResponse
    suspend fun dataSession(name: String, accessToken: String, onBoardingState: Boolean)
    suspend fun fetchDetailMovie(
        movieId: Int? = null
    ): DetailMovieResponse
    suspend fun fetchSearch(query: String): Flow<PagingData<DataSearchMovie>>
    suspend fun insertCart(cartEntity: CartEntity)

    suspend fun deleteCart(cartEntity: CartEntity)

    suspend fun fetchCart(id: Int): Flow<List<CartEntity>>

    suspend fun insertWishList(wishListEntity: WishListEntity)

    suspend fun fetchWishList(id: Int): Flow<List<WishListEntity>>

    suspend fun deleteWishlist(wishListEntity: WishListEntity)

    fun putWishlistState(state: Boolean)
//    fun getWishlistState(): Boolean
    fun getProfileName(): String
    fun saveProfileName(string: String)
    fun getOnBoardingState(): Boolean
    fun saveOnBoardingState(state: Boolean)
    fun getUid(): String
    fun saveUid(string: String)
    suspend fun checkWishlist(movieId: Int): Int
    suspend fun updateCheckCart(cartId: Int, value: Boolean)
    suspend fun updateTotalPrice(): Int
}