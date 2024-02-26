package com.example.core.local.preferences

interface SharedPreferencesHelper {

    companion object {
        const val PREFS_NAME = "MySharedPreferences"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ON_BOARDING_STATE = "on_boarding_state"
        private const val LANGUAGE_STATUS= "language_status"
        private const val THEME_STATUS= "theme_status"
        private const val PROFILE_KEY = "profile_key"
        private const val WISHLIST_STATE = "wishlist_state"
    }

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