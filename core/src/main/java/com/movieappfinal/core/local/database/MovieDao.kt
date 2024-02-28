package com.movieappfinal.core.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Query("SELECT * FROM cart_table WHERE userId = :id")
    fun retrieveAllCart(id: Int): Flow<List<CartEntity>>

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllCart()

    @Delete
    suspend fun deleteCart(cart: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishList(wishList: WishListEntity)

    @Query("SELECT * FROM wishlist_table WHERE userId = :id")
    fun retrieveAllWishList(id: Int): Flow<List<WishListEntity>>

    @Query("DELETE FROM wishlist_table")
    suspend fun deleteAllWishList()

    @Delete
    suspend fun deleteWishlist(wishList: WishListEntity)

    @Query("SELECT count(*) FROM wishlist_table WHERE productId = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("UPDATE cart_table SET isChecked = :value WHERE cartId = :cartId")
    suspend fun updateCheckCart(cartId: Int, value: Boolean)
}