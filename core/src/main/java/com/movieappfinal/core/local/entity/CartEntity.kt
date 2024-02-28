package com.movieappfinal.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieappfinal.core.utils.Constant.tableCartName


@Entity(tableName = tableCartName)
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cartId")
    val cartId: Int = 0,
    @ColumnInfo(name = "movieId")
    val movieId: Int = 0,
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "productName")
    val movieTitle: String = "",
    @ColumnInfo(name = "productPrice")
    val moviePrice: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: Int = 0,
    @ColumnInfo(name = "isChecked")
    val isChecked: Boolean = false
)