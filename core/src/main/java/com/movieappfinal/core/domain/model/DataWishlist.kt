package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataWishlist(
    val wishlistId: Int = 0,
    val movieId: Int = 0,
    val image: String = "",
    val movieTitle: String = "",
    val moviePrice: Int = 0,
    val userId: Int = 0,
    val releaseDate: String = "",
    val wishlist: Boolean = false,
):Parcelable