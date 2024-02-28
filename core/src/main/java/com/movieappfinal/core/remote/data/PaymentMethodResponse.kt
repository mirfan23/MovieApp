package com.movieappfinal.core.remote.data

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class PaymentMethodResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<DataPayment>,
    @SerializedName("message")
    val message: String
) : Parcelable {
    @Keep
    @Parcelize
    data class DataPayment(
        @SerializedName("item")
        val item: List<Item>,
        @SerializedName("title")
        val title: String
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Item(
            @SerializedName("image")
            val image: String,
            @SerializedName("isMaintenance")
            val isMaintenance: Boolean,
            @SerializedName("label")
            val label: String,
            @SerializedName("status")
            val status: Boolean
        ) : Parcelable
    }
}