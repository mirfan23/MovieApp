package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataMovieTransaction(
    var movieId: Int = 0,
    var userId: String = "",
    var userName: String = "",
    var itemName: String = "",
    var itemPrice: Int = 0,
    var totalPrice: Int = 0,
    var transactionTime: String = ""
) : Parcelable
