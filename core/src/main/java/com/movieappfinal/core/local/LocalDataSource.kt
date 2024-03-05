package com.movieappfinal.core.local

import com.movieappfinal.core.local.database.MovieDao
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity
import com.movieappfinal.core.local.preferences.SharedPreferencesHelper
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val preference: SharedPreferencesHelper,
    private val dao: MovieDao
) {

    suspend fun insertCart(cartEntity: CartEntity) {
        dao.insertCart(cartEntity)
    }
    fun fetchCart(id: String): Flow<List<CartEntity>> = dao.retrieveAllCart(id)

    suspend fun deleteCart(cartEntity: CartEntity) {
        dao.deleteCart(cartEntity)
    }

    suspend fun insertWishList(wishListEntity: WishListEntity) {
        dao.insertWishList(wishListEntity)
    }

    fun fetchWishList(id: String): Flow<List<WishListEntity>> = dao.retrieveAllWishList(id)

    suspend fun deleteWishlist(wishListEntity: WishListEntity) {
        dao.deleteWishlist(wishListEntity)
    }
    fun getOnBoardingState(): Boolean = preference.getOnBoardingState()

    fun saveOnBoardingState(state: Boolean) {
        preference.putOnBoardingState(state)
    }

//    fun getWishlistState(): Boolean = preference.getWishlistState()

    fun putWishlistState(state: Boolean) {
        preference.putWishlistState(state)
    }

    fun saveRefreshToken(token: String) {
        preference.putRefreshToken(token)
    }

    fun putUid(token: String) {
        preference.putUid(token)
    }

    fun getUid(): String = preference.getUid()

    fun saveProfileName(name: String) {
        preference.putProfileName(name)
    }

    fun getProfileName(): String = preference.getProfileName()

    suspend fun checkWishlist(movieId: Int): Int = dao.checkFavorite(movieId)

    suspend fun updateCheckCart(cartId: Int, value: Boolean) = dao.updateCheckCart(cartId, value)


    fun updateTotalPriceChecked(): Int = dao.updateTotalPriceChecked()

}