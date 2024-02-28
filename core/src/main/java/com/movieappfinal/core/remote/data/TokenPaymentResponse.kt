package com.movieappfinal.core.remote.data

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class TokenPaymentResponse : ArrayList<TokenPaymentResponse.TokenPaymentResponseItem>(){
    @Keep
    @Parcelize
    data class TokenPaymentResponseItem(
        @SerializedName("price")
        val price: Int,
        @SerializedName("token")
        val token: Int
    ) : Parcelable
}