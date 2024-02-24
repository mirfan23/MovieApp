package com.movieappfinal.core.domain.usecase

import com.example.core.local.preferences.SharedPreferencesHelper

class AppInteractor(
//    private val repository: AuthRepository,
//    private val productRepo: ProductRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
//    private val firebaseRepository: FirebaseRepository
) : AppUseCase
 {
     override fun saveAccessToken(string: String) {
//         repository.saveAccessToken(string)
     }

     override fun saveRefreshToken(string: String) {
//         repository.saveRefreshToken(string)
     }

     override fun saveProfileName(string: String) {
//         repository.saveProfileName(string)
     }

     override fun getProfileName(): String { TODO() }
//     = repository.getProfileName()

     override fun saveOnBoardingState(value: Boolean) {
//         repository.saveOnBoardingState(value)
     }

     override fun getOnBoardingState(): Boolean {TODO()}
//     = repository.getOnBoardingState()
     override fun putWishlistState(value: Boolean) {
//         productRepo.putWishlistState(value)
     }

     override fun getWishlistState(): Boolean {TODO()}
//     = productRepo.getWishlistState()


     override fun putThemeStatus(value: Boolean) {
         sharedPreferencesHelper.putThemeStatus(value)
     }

     override fun getThemeStatus(): Boolean = sharedPreferencesHelper.getThemeStatus()


     override fun putLanguageStatus(value: String) {
         sharedPreferencesHelper.putLanguageStatus(value)
     }

     override fun getLanguageStatus(): String = sharedPreferencesHelper.getLanguageStatus()

     override fun putAccessToken(value: String) {
         sharedPreferencesHelper.putAccessToken(value)
     }

     override fun getAccessToken(): String = sharedPreferencesHelper.getAccessToken()


     override fun putRefreshToken(value: String) {
         sharedPreferencesHelper.putRefreshToken(value)
     }

     override fun getRefreshToken(): String = sharedPreferencesHelper.getRefreshToken()

     override fun clearAllSession() {
         sharedPreferencesHelper.clearAllSession()
     }
 }