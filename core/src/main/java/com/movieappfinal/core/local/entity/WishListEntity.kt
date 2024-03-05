package com.movieappfinal.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieappfinal.core.utils.Constant.tableWishListName

@Entity(tableName = tableWishListName)
data class WishListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wishlistId")
    val wishlistId: Int = 0,
    @ColumnInfo(name = "productId")
    val movieId: Int = 0,
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "productName")
    val movieTitle: String = "",
    @ColumnInfo(name = "productPrice")
    val moviePrice: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: String = "",
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String = "",
    @ColumnInfo(name = "wishlist")
    val wishlist: Boolean = false,
)