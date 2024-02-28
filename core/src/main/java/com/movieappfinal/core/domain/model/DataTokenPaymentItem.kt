package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataTokenPaymentItem(
    var token: Int = 0,
    var price: Int = 0
) : Parcelable
