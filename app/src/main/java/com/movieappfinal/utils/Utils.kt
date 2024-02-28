package com.movieappfinal.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.android.material.materialswitch.MaterialSwitch
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
fun String.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())

fun MaterialSwitch.checkIf(state: Boolean) {
    this.isChecked = state
}

fun View.visibleIf(state: Boolean) {
    this.isVisible = state
}

fun String?.validateEmail(): Boolean {
    val emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    val isValid = this?.let { emailPattern.toRegex().matches(it) }
    return isValid == true
}

fun String?.validatePassword(): Boolean {
    val isValid = this?.let {it.length >= 8}
    /**
     * akan digunakan kembali
     *
     * if (password.none { it.isUpperCase() }) {
     *             showError("Password setidaknya terdapat satu huruf kapital")
     *             return false
     *         }
     *         if (password.none { !it.isLetterOrDigit() }) {
     *             showError("Password harus mengandung setidaknya satu karakter khusus")
     *             return false
     *         }
     */

    return isValid == true
}

fun String.validateRequired(): Boolean = this.isNotEmpty() == true

/**
 * belum ketemu cara makenya di adapter jadi di simpen dulu changeGenre pake getstringya
 */

//fun Context.changeGenre(genreId: Int): String {
//    val genres = mapOf(
//        28 to getString(R.string.action_genre),
//        12 to getString(R.string.adventure_genre),
//        16 to getString(R.string.animation_genre),
//        35 to getString(R.string.comedy_genre),
//        80 to getString(R.string.crime_genre),
//        99 to getString(R.string.documentary_genre),
//        18 to getString(R.string.drama_genre),
//        10751 to getString(R.string.family_genre),
//        14 to getString(R.string.fantasy_genre),
//        36 to getString(R.string.history_genre),
//        27 to getString(R.string.horror_genre),
//        10402 to getString(R.string.music_genre),
//        9648 to getString(R.string.mystery_genre),
//        10749 to getString(R.string.romance_genre),
//        878 to getString(R.string.science_fiction_genre),
//        10770 to getString(R.string.tv_movie_genre),
//        53 to getString(R.string.thriller_genre),
//        10752 to getString(R.string.war_genre),
//        37 to getString(R.string.western_genre)
//    )
//    return genres[genreId] ?: "Unknown Genre"
//}
/**
 * nanti diubah sama yang diatas setelah ketemu cara pakenya
 */

fun changeGenre(genreId: Int): String {
    val genres = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )
    return genres[genreId] ?: "Unknown Genre"
}