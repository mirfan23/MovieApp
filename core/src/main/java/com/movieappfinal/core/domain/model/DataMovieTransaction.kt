package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataMovieTransaction(
    var userId: String,
    var userName: String,
    var transactionTime: String,
    var movieId: List<Int>,
    var itemName: List<String>,
    var itemPrice: List<Int>,
    var totalPrice: Int
) : Parcelable
