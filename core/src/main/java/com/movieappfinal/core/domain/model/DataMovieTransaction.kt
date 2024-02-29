package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataMovieTransaction(
    var userName: String = "",
    var itemName: String = "",
    var itemPrice: Int = 0,
    var totalAmount: Int = 0
): Parcelable
