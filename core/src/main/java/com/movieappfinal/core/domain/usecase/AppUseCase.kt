package com.movieappfinal.core.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataProfile
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.state.UiState
import kotlinx.coroutines.flow.Flow

interface AppUseCase {

    suspend fun fetchPopularMovie(): DataPopularMovie
    suspend fun fetchNowPlayingMovie(): DataNowPlaying
    suspend fun fetchTrendingMovie(): DataTrendingMovie
    suspend fun fetchDetailMovie(movieId: Int): DataDetailMovie
    suspend fun signUpFirebase(email: String, password: String): Flow<Boolean>
    suspend fun signInFirebase(email: String, password: String): Flow<Boolean>
    suspend fun getCurrentUser(): DataProfile?
    suspend fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean>
    suspend fun insertCart(productCart: DataCart)
    suspend fun fetchCart(id: Int): Flow<UiState<List<DataCart>>>
    suspend fun deleteCart(dataCart: DataCart)
    suspend fun insertWishList(dataWishList: DataWishlist)
    suspend fun fetchWishList(id: Int): Flow<UiState<List<DataWishlist>>>
    suspend fun deleteWishlist(dataWishList: DataWishlist)
    fun dataSession(): DataSession
    fun saveOnBoardingState(value: Boolean)
    fun getOnBoardingState(): Boolean
    fun putWishlistState(value: Boolean)
    fun getWishlistState(): Boolean
    fun putThemeStatus(value: Boolean)
    fun getThemeStatus(): Boolean
    fun putLanguageStatus(value: String)
    fun saveProfileName(string: String)
    fun getProfileName(): String
    fun getLanguageStatus(): String
    fun putUid(value: String)
    fun getUid(): String
    fun clearAllSession()
}