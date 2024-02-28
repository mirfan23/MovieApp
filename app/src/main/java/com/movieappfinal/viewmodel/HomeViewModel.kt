package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.state.UiState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.utils.asMutableStateFLow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val useCase: AppUseCase) : ViewModel() {
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

    fun fetchSearch(query: String) = runBlocking{ useCase.fetchSearch(query).cachedIn(viewModelScope) }



}