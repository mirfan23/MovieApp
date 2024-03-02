package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.firebase.database.FirebaseDatabase
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.repository.FirebaseRepository
import com.movieappfinal.core.domain.state.UiState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.utils.asMutableStateFLow
import com.movieappfinal.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class HomeViewModel(
    private val useCase: AppUseCase,
    private val fireRepo: FirebaseRepository
) : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference
    private val movieReference = database.child("movie_transaction")

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice = _totalPrice.asStateFlow()

    private val _responsePopularMovie: MutableStateFlow<UiState<DataPopularMovie>> =
        MutableStateFlow(UiState.Empty)
    val responsePopularMovie = _responsePopularMovie.asStateFlow()

    private val _responseNowPlaying: MutableStateFlow<UiState<DataNowPlaying>> =
        MutableStateFlow(UiState.Empty)
    val responseNowPlaying = _responseNowPlaying.asStateFlow()

    private val _responseTrendingMovie: MutableStateFlow<UiState<DataTrendingMovie>> =
        MutableStateFlow(UiState.Empty)
    val responseTrendingMovie = _responseTrendingMovie.asStateFlow()

    private val _responseDetail: MutableStateFlow<UiState<DataDetailMovie>> =
        MutableStateFlow(UiState.Empty)
    val responseDetail = _responseDetail.asStateFlow()

    private var dataCart: DataCart? = null

    private var dataWishlist: DataWishlist? = null

    private var listCart: MutableList<DataCart> = mutableListOf()

    private val _theme = MutableStateFlow(false)
    val theme = _theme.asStateFlow()

    private val _language = MutableStateFlow(false)
    val language = _language.asStateFlow()

    fun getCurrentUser() = runBlocking { useCase.getCurrentUser() }

    fun fetchPopularMovie() {
        viewModelScope.launch {
            _responsePopularMovie.asMutableStateFLow {
                useCase.fetchPopularMovie()
            }
        }
    }

    fun fetchNowPlaying() {
        viewModelScope.launch {
            _responseNowPlaying.asMutableStateFLow {
                useCase.fetchNowPlayingMovie()
            }
        }
    }

    fun fetchTrendingMovie() {
        viewModelScope.launch {
            _responseTrendingMovie.asMutableStateFLow {
                useCase.fetchTrendingMovie()
            }
        }
    }

    fun fetchDetail(movieId: Int) {
        viewModelScope.launch {
            _responseDetail.asMutableStateFLow {
                useCase.fetchDetailMovie(movieId)
            }
        }
    }

    fun setDataWishlist(data: DataWishlist) {
        dataWishlist = data
    }

    fun insertCart() {
        viewModelScope.launch {
            dataCart?.let { useCase.insertCart(it) }
        }
    }

    fun fetchCart() = runBlocking {
        val uid = useCase.getUid()
        useCase.fetchCart(uid.hashCode())
    }

    fun insertWishList() {
        viewModelScope.launch {
            dataWishlist?.let { useCase.insertWishList(it) }
        }
    }

    fun fetchWishList() = runBlocking {
        val uid = useCase.getUid()
        useCase.fetchWishList(uid.hashCode())
    }

    fun removeCart(dataCart: DataCart) {
        viewModelScope.launch {
            useCase.deleteCart(dataCart)
        }
    }

    fun removeWishlistDetail() {
        viewModelScope.launch {
            dataWishlist?.let {
                useCase.deleteWishlist(it)
            }
        }
    }

    fun removeWishlist(data: DataWishlist) {
        viewModelScope.launch {
            useCase.deleteWishlist(data)
        }
    }

    fun setDataCart(data: DataCart) {
        dataCart = data
    }

    fun setDataListCart(list: List<DataCart>) {
        listCart.clear()
        listCart.addAll(list)
    }

    fun putWishlistState(value: Boolean) {
        useCase.putWishlistState(value)
    }

    fun fetchSearch(query: String) =
        runBlocking { useCase.fetchSearch(query).cachedIn(viewModelScope) }

    fun getThemeStatus() {
        _theme.update { useCase.getThemeStatus() }
    }

    fun putThemeStatus(value: Boolean) {
        useCase.putThemeStatus(value)
    }

    fun getLanguageStatus(): Boolean {
        return useCase.getLanguageStatus().equals(Constant.LANGUAGE_IN, true)
    }

    fun putLanguageStatus(value: String) {
        useCase.putLanguageStatus(value)
    }

    fun deleteAccount() = runBlocking { useCase.deleteAccount() }

    fun sendToDatabase(data: DataTokenTransaction) = runBlocking {
        useCase.sendToDatabase(data)
//        fireRepo.sendDataToDatabase(data)
    }

    fun checkWishlist(movieId: Int) =
        runBlocking { useCase.checkWishlist(movieId) > 0 }

    fun isMovieAlreadyPurchased(movieId: String): Boolean {
        var isPurchased = false
        viewModelScope.launch {
            try {
                val snapshot = movieReference.orderByChild("movieId").equalTo(movieId).get().await()
                println("MASUK $snapshot")
                isPurchased = snapshot.exists()
            } catch (e: Exception) {
                // Handle any errors
                println("MASUK : Error checking movie transaction: $e")
            }
        }
        return isPurchased
    }

    fun updateCheckCart(cartId: Int, value: Boolean) {
        viewModelScope.launch {
            useCase.updateCheckCart(cartId, value)
        }
    }

    fun updateTotalPrice() {
        viewModelScope.launch {
            val totalPrice = useCase.updateTotalPrice()
            _totalPrice.value = totalPrice
        }
    }

    fun firebaseAnalytic(screenName: String) {
        viewModelScope.launch {
            fireRepo.logScreenView(screenName)
        }
    }

//    fun fetchTotalPrice() {
//        viewModelScope.launch {
//            updateTotalPrice(checked = false)
//        }
//    }

}