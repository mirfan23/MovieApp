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