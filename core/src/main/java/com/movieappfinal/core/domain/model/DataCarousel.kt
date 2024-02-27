package com.movieappfinal.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class DataCarousel(
    var id: Int = 0,
    var genreIds: List<Int> = listOf(),
    var title: String = "",
    var backdrop: String = "",
) : Parcelable