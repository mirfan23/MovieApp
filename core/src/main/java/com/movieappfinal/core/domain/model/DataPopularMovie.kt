package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class DataPopularMovie(
    var title: String = "",
    var itemsPopular: List<DataPopularMovieItem> = listOf(),
): Parcelable