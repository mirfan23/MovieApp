package com.movieappfinal.core.local.preferences

interface SharedPreferencesHelper {

    fun putThemeStatus(value: Boolean)
    fun getThemeStatus(): Boolean
    fun putLanguageStatus(value: String)
    fun getLanguageStatus(): String
    fun putUid(value: String)
    fun getUid(): String
    fun putRefreshToken(value: String)
    fun getRefreshToken(): String
    fun putOnBoardingState(value: Boolean)
    fun getOnBoardingState(): Boolean
    fun putProfileName(value: String)
    fun getProfileName(): String
    fun putWishlistState(value: Boolean)
    fun getWishlistState(): Boolean
    fun clearAllSession()
}