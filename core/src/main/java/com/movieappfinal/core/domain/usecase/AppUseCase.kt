package com.movieappfinal.core.domain.usecase

interface AppUseCase {

//    fun dataSession(): DataSession
    fun saveAccessToken(string: String)
    fun saveRefreshToken(string: String)
    fun saveProfileName(string: String)
    fun getProfileName(): String
    fun saveOnBoardingState(value: Boolean)
    fun getOnBoardingState(): Boolean
    fun putWishlistState(value: Boolean)
    fun getWishlistState(): Boolean
    fun putThemeStatus(value: Boolean)
    fun getThemeStatus(): Boolean
    fun putLanguageStatus(value: String)
    fun getLanguageStatus(): String
    fun putAccessToken(value: String)
    fun getAccessToken(): String?
    fun putRefreshToken(value: String)
    fun getRefreshToken(): String?
    fun clearAllSession()
}