package com.movieappfinal.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieappfinal.core.local.entity.CartEntity
import com.movieappfinal.core.local.entity.WishListEntity

@Database(entities = [CartEntity::class, WishListEntity::class], version = 3)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}