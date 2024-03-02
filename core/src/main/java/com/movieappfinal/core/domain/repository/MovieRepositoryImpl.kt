package com.movieappfinal.core.domain.repository

import androidx.paging.PagingData
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.local.LocalDataSource
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity
import com.movieappfinal.core.remote.RemoteDataSource
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.data.TrendingMovieResponse
import com.movieappfinal.core.utils.safeApiCall
import com.movieappfinal.core.utils.safeDataCall
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : MovieRepository {
    override suspend fun fetchPopularMovie(): PopularMovieResponse = safeApiCall {
        remote.fetchPopularMovie()
    }

    override suspend fun fetchNowPlayingMovie(): NowPlayingResponse = safeApiCall {
        remote.fetchNowPlayingMovie()
    }

    override suspend fun fetchTrendingMovie(): TrendingMovieResponse = safeApiCall {
        remote.fetchTrendingMovie()
    }

    override suspend fun dataSession(name: String, accessToken: String, onBoardingState: Boolean) {}
    override suspend fun fetchDetailMovie(movieId: Int?): DetailMovieResponse = safeApiCall {
        remote.fetchDetailMovie(movieId)
    }

    override suspend fun fetchSearch(query: String): Flow<PagingData<DataSearchMovie>> =
        safeDataCall {
            remote.fetchSearch(query)
        }

    override suspend fun insertCart(cartEntity: CartEntity) {
        local.insertCart(cartEntity)
    }

    override suspend fun deleteCart(cartEntity: CartEntity) {
        local.deleteCart(cartEntity)
    }

    override suspend fun fetchCart(id: Int): Flow<List<CartEntity>> = safeDataCall {
        local.fetchCart(id)
    }

    override suspend fun insertWishList(wishListEntity: WishListEntity) {
        local.insertWishList(wishListEntity)
    }

    override suspend fun fetchWishList(id: Int): Flow<List<WishListEntity>> = safeDataCall {
        local.fetchWishList(id)
    }

    override suspend fun deleteWishlist(wishListEntity: WishListEntity) {
        local.deleteWishlist(wishListEntity)
    }

    override fun putWishlistState(state: Boolean) {
        local.putWishlistState(state)
    }

//    override fun getWishlistState(): Boolean = local.getWishlistState()

    override fun getProfileName(): String = local.getProfileName()

    override fun saveProfileName(string: String) {
        local.saveProfileName(string)
    }

    override fun getOnBoardingState(): Boolean = local.getOnBoardingState()

    override fun saveOnBoardingState(state: Boolean) {
        local.saveOnBoardingState(state)
    }

    override fun getUid(): String = local.getUid()

    override fun saveUid(string: String) {
        local.putUid(string)
    }

    override suspend fun checkWishlist(movieId: Int): Int = safeDataCall {
        local.checkWishlist(movieId)
    }

    override suspend fun updateCheckCart(cartId: Int, value: Boolean) {
        local.updateCheckCart(cartId, value)
    }

    override suspend fun updateTotalPrice(): Int {
        return local.updateTotalPriceChecked()
    }
}