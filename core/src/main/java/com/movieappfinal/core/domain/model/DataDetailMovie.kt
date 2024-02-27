package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class DataDetailMovie(
    var id: Int = 0,
    var backdrop: String = "",
    var title: String = "",
    var releaseDate: String = "",
    var poster: String = "",
    var overview: String = "",
    var runtime: Int = 0,
    var genres: List<DataGenre>,
):Parcelable
