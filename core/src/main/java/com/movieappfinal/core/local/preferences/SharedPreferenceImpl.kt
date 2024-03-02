package com.movieappfinal.core.local.preferences

import android.content.SharedPreferences

class SharedPreferenceImpl(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesHelper {
    companion object {
        const val PREFS_NAME = "MySharedPreferences"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ON_BOARDING_STATE = "on_boarding_state"
        private const val LANGUAGE_STATUS= "language_status"
        private const val THEME_STATUS= "theme_status"
        private const val PROFILE_KEY = "profile_key"
        private const val WISHLIST_STATE = "wishlist_state"
        private const val KEY_UID = "key_uid"
    }

    override fun putThemeStatus(value: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_STATUS, value).apply()
    }

    override fun getThemeStatus(): Boolean {
        return sharedPreferences.getBoolean(THEME_STATUS, false)
    }

    override fun putLanguageStatus(value: String) {
        sharedPreferences.edit().putString(LANGUAGE_STATUS, value).apply()
    }

    override fun getLanguageStatus(): String {
        return sharedPreferences.getString(LANGUAGE_STATUS, "").toString()
    }

    override fun putUid(value: String) {
        sharedPreferences.edit().putString(KEY_UID, value).apply()
    }

    override fun getUid(): String {
        return sharedPreferences.getString(KEY_UID, "").toString()
    }

    override fun putRefreshToken(value: String) {
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, value).apply()
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, "").toString()
    }

    override fun putOnBoardingState(value: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ON_BOARDING_STATE, value).apply()
    }

    override fun getOnBoardingState(): Boolean {
        return sharedPreferences.getBoolean(KEY_ON_BOARDING_STATE, false)
    }

    override fun putProfileName(value: String) {
        return sharedPreferences.edit().putString(PROFILE_KEY, value).apply()
    }

    override fun getProfileName(): String {
        return sharedPreferences.getString(PROFILE_KEY, "").toString()
    }

    override fun putWishlistState(value: Boolean) {
        sharedPreferences.edit().putBoolean(WISHLIST_STATE, value).apply()
    }

//    override fun getWishlistState(movieId: Int) {
//        TODO("Not yet implemented")
//    }

    override fun getWishlistState(): Boolean {
        return sharedPreferences.getBoolean(WISHLIST_STATE, false)
    }

    override fun clearAllSession() {
        sharedPreferences.edit().apply{
            remove(KEY_UID)
            remove(PROFILE_KEY)
            apply()
        }
    }

}