package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter

@Keep
@Parcelize
data class DataTokenTransaction(
    var userId: String = "",
    var tokenAmount: String = "",
    var tokenPrice: String = "",
    var paymentMethod: String = "",
    var userName: String = "",
    var transactionTime: String = "",
): Parcelable
