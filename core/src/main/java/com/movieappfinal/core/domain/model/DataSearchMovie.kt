package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataSearchMovie(
    var movieId: Int? = null,
    var genreIds: List<Int>? = null,
    var title: String? = null,
    var backdrop: String? = null,
    var overview: String? = null,
    var poster: String? = null,
    var releaseDate: String? = null,
    var popularity: Int? = null
) : Parcelable