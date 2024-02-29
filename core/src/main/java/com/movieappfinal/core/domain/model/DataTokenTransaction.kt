package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataTokenTransaction(
    var tokenAmount: String,
    var tokenPrice: String,
    var paymentMethod: String,
    var userName: String
): Parcelable
