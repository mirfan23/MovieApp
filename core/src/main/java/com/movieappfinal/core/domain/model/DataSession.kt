package com.movieappfinal.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataSession (
    var name: String = "",
    var uid: String = "",
    var onBoardingState : Boolean = false,
) : Parcelable