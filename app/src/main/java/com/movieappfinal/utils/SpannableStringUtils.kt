package com.movieappfinal.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.movieappfinal.R
import com.movieappfinal.utils.Constant.LANGUAGE_IN

class SpannableStringUtils {
    companion object {
        fun applyCustomTextColor(locale: String, context: Context, fullText: String): SpannableString {
            val spannableString = SpannableStringBuilder(fullText)

            val tncText = if (locale == LANGUAGE_IN) "Syarat & Ketentuan" else "Terms & Conditions"
            val startTnc = fullText.indexOf(tncText)
            val endtnc = startTnc + tncText.length

            val policyText = if (locale== LANGUAGE_IN) "Kebijakan Privasi" else "Privacy Policy"
            val startPolicy = fullText.indexOf(policyText)
            val endPolicy = startPolicy + policyText.length

            val customColor = ContextCompat.getColor(context, R.color.black)

            val tncClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    // Handle click for "Syarat & Ketentuan"
                    openUrl(context, "https://www.youtube.com")
                }
            }
            val policyClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    // Handle click for "Kebijakan Privasi"
                    openUrl(context, "https://www.bing.com")
                }
            }
            spannableString.setSpan(tncClickableSpan, startTnc, endtnc, 0)
            spannableString.setSpan(policyClickableSpan, startPolicy, endPolicy, 0)

            spannableString.setSpan(
                ForegroundColorSpan(customColor),
                startTnc,
                endtnc,
                0
            )
            spannableString.setSpan(
                ForegroundColorSpan(customColor),
                startPolicy,
                endPolicy,
                0
            )

            return SpannableString(spannableString)
        }

        private fun openUrl(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)}
    }
}
