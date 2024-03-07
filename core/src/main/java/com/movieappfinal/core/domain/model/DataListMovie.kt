package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataListMovie(
    var listDataMovie: List<DataMoviePayment>,
    var totalPayment: Int = 0
):Parcelable
