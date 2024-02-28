package com.example.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class DataPaymentMethod (
    var item: List<DataPaymentMethodItem> = listOf(),
    val title: String = ""
): Parcelable