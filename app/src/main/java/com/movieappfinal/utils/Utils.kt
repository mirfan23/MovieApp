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