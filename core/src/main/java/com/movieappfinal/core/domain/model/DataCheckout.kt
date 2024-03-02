package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataCheckout(
    var movieId: Int = 0,
    var image: String = "",
    var itemName: String = "",
    var itemPrice: Int = 0
): Parcelable
