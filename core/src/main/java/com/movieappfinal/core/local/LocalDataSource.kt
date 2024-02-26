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

    fun putUid(token: String) {
        preference.putUid(token)
    }

    fun getUid(): String = preference.getUid()

    fun saveProfileName(name: String) {
        preference.putProfileName(name)
    }

    fun getProfileName(): String = preference.getProfileName()
}