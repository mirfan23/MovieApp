package com.movieappfinal.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.DataPaymentMethod
import com.movieappfinal.core.local.preferences.SharedPreferencesHelper
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.Gson
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataProfile
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.domain.model.DataSession
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.repository.MovieRepository
import com.movieappfinal.core.domain.state.UiState
import com.movieappfinal.core.remote.data.PaymentMethodResponse
import com.movieappfinal.core.remote.data.TokenPaymentResponse
import com.movieappfinal.core.utils.DataMapper.toEntity
import com.movieappfinal.core.utils.DataMapper.toUIData
import com.movieappfinal.core.utils.DataMapper.toUiData
import com.movieappfinal.core.utils.safeApiCall
import com.movieappfinal.core.utils.safeDataCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AppInteractor(
    private val movieRepo: MovieRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val firebaseRepo: FirebaseRepository
) : AppUseCase {
    override suspend fun fetchPopularMovie(): DataPopularMovie = safeApiCall {
        movieRepo.fetchPopularMovie().toUIData()
    }

    override suspend fun fetchNowPlayingMovie(): DataNowPlaying = safeApiCall {
        movieRepo.fetchNowPlayingMovie().toUIData()
    }

    override suspend fun fetchTrendingMovie(): DataTrendingMovie = safeApiCall {
        movieRepo.fetchTrendingMovie().toUIData()
    }

    override suspend fun fetchDetailMovie(movieId: Int): DataDetailMovie = safeApiCall {
        movieRepo.fetchDetailMovie(movieId).toUIData()
    }

    override suspend fun signUpFirebase(email: String, password: String): Flow<Boolean> =
        safeDataCall {
            firebaseRepo.signUpFirebase(email, password)
        }

    override suspend fun signInFirebase(email: String, password: String): Flow<Boolean> =
        safeDataCall {
            firebaseRepo.signInFirebase(email, password)
        }

    override suspend fun sendTokenToDatabase(
        dataTokenTransaction: DataTokenTransaction,
        userId: String
    ): Flow<Boolean> =
        safeDataCall {
            firebaseRepo.sendDataToDatabase(dataTokenTransaction, userId)
        }

    override suspend fun sendMovieToDatabase(
        dataMovieTransaction: DataMovieTransaction,
        userId: String
    ): Flow<Boolean> = safeDataCall {
        firebaseRepo.sendMovieToDataBase(dataMovieTransaction, userId)
    }

    override suspend fun getTokenFromDatabase(userId: String): Flow<Int> = safeDataCall{
        firebaseRepo.getTokenFromFirebase(userId)
    }

    override suspend fun getMovieTransactionFromDatabase(userId: String): Flow<Int> = safeDataCall {
        firebaseRepo.getMovieTransactionFromFirebase(userId)
    }

    override suspend fun deleteAccount(): Flow<Boolean> =
        safeDataCall {
            firebaseRepo.deleteAccount()
        }

    override suspend fun getCurrentUser(): DataProfile? {
        val user = firebaseRepo.getCurrentUser()
        return user?.let { it.displayName?.let { displayName -> DataProfile(displayName, it.uid) } }
    }

    override suspend fun updateProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> =
        safeDataCall {
            firebaseRepo.updateProfile(userProfileChangeRequest)
        }

    override suspend fun fetchSearch(query: String): Flow<PagingData<DataSearchMovie>> =
        safeDataCall {
            movieRepo.fetchSearch(query)
        }

    override suspend fun insertCart(productCart: DataCart) {
        movieRepo.insertCart(productCart.toEntity())
    }

    override suspend fun deleteCart(dataCart: DataCart) {
        movieRepo.deleteCart(dataCart.toEntity())
    }

    override suspend fun fetchCart(id: String): Flow<UiState<List<DataCart>>> = safeDataCall {
        movieRepo.fetchCart(id).map { data ->
            val mapped = data.map { cartEntity -> cartEntity.toUIData() }
            UiState.Success(mapped)
        }.flowOn(Dispatchers.IO).catch { throwable -> UiState.Error(throwable) }
    }

    override suspend fun insertWishList(dataWishList: DataWishlist) {
        movieRepo.insertWishList(dataWishList.toEntity())
    }

    override suspend fun fetchWishList(id: String): Flow<UiState<List<DataWishlist>>> =
        safeDataCall {
            movieRepo.fetchWishList(id).map { data ->
                val mapped = data.map { wishListEntity -> wishListEntity.toUIData() }
                UiState.Success(mapped)
            }.flowOn(Dispatchers.IO).catch { throwable -> UiState.Error(throwable) }
        }

    override suspend fun deleteWishlist(dataWishList: DataWishlist) {
        movieRepo.deleteWishlist(dataWishList.toEntity())
    }

    override suspend fun getConfigPayment(): Flow<List<DataTokenPaymentItem>> = safeDataCall {
        firebaseRepo.getConfigPayment().map { data ->
            val response = Gson().fromJson(data, TokenPaymentResponse::class.java)
            response.map { it.toUiData() }.toList()
        }
    }

    override suspend fun getConfigStatusUpdate(): Flow<Boolean> =
        firebaseRepo.getConfigStatusUpdate()

    override suspend fun getConfigPaymentMethod(): Flow<List<DataPaymentMethod>> = safeDataCall {
        firebaseRepo.getConfigPaymentMethod().map { string ->
            val response = Gson().fromJson(string, PaymentMethodResponse::class.java)
            response.data.map { data -> data.toUIData() }.toList()
        }
    }

    override suspend fun getConfigStatusUpdatePayment(): Flow<Boolean> =
        firebaseRepo.getConfigStatusUpdatePayment()

    override fun dataSession(): DataSession {
        val name = movieRepo.getProfileName()
        val uid = movieRepo.getUid()
        val onBoardingState = movieRepo.getOnBoardingState()
        val triple: Triple<String, String, Boolean> = Triple(name, uid, onBoardingState)
        return triple.toUIData()
    }

    override fun saveOnBoardingState(value: Boolean) {
        movieRepo.saveOnBoardingState(value)
    }

    override fun getOnBoardingState(): Boolean = movieRepo.getOnBoardingState()
    override fun putWishlistState(value: Boolean) {
        movieRepo.putWishlistState(value)
    }

//    override fun getWishlistState(): Boolean = movieRepo.getWishlistState()


    override fun getThemeStatus(): Boolean = sharedPreferencesHelper.getThemeStatus()
    override fun putThemeStatus(value: Boolean) {
        sharedPreferencesHelper.putThemeStatus(value)
    }

    override fun getLanguageStatus(): String = sharedPreferencesHelper.getLanguageStatus()

    override fun putLanguageStatus(value: String) {
        sharedPreferencesHelper.putLanguageStatus(value)
    }


    override fun clearAllSession() {
        sharedPreferencesHelper.clearAllSession()
    }

    override fun putUid(value: String) {
        sharedPreferencesHelper.putUid(value)
    }

    override fun getUid(): String = sharedPreferencesHelper.getUid()

    override fun saveProfileName(string: String) {
        movieRepo.saveProfileName(string)
    }

    override fun getProfileName(): String = movieRepo.getProfileName()

    override suspend fun checkWishlist(movieId: Int): Int = movieRepo.checkWishlist(movieId)

    override suspend fun updateCheckCart(cartId: Int, value: Boolean) {
        movieRepo.updateCheckCart(cartId, value)
    }

    override suspend fun updateTotalPrice(): Int = safeDataCall {
        movieRepo.updateTotalPrice()
    }
}