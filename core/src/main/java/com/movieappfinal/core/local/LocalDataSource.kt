package com.movieappfinal.core.local

import com.example.core.local.preferences.SharedPreferencesHelper

class LocalDataSource(
    private val preference: SharedPreferencesHelper,
) {
    fun getOnBoardingState(): Boolean = preference.getOnBoardingState()

    fun saveOnBoardingState(state: Boolean) {
        preference.putOnBoardingState(state)
    }

    fun getWishlistState(): Boolean = preference.getWishlistState()

    fun putWishlistState(state: Boolean) {
        preference.putWishlistState(state)
    }

    fun saveRefreshToken(token: String) {
        preference.putRefreshToken(token)
    }

    fun saveAccessToken(token: String) {
        preference.putAccessToken(token)
    }

    fun getAccessToken(): String = preference.getAccessToken()

    fun saveProfileName(name: String) {
        preference.putProfileName(name)
    }

    fun getProfileName(): String = preference.getProfileName()
}