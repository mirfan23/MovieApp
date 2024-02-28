package com.movieappfinal.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.android.material.materialswitch.MaterialSwitch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
fun String.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())

fun MaterialSwitch.checkIf(state: Boolean) {
    this.isChecked = state
}

fun View.visibleIf(state: Boolean) {
    this.isVisible = state
}

fun currency(number: Int): String {
    val currencyIn = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp "
    formatRp.groupingSeparator = '.'

    currencyIn.decimalFormatSymbols = formatRp
    return currencyIn.format(number).dropLast(3)
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