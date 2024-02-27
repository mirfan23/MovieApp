package com.movieappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.state.UiState
import com.movieappfinal.core.domain.usecase.AppUseCase
import com.movieappfinal.core.utils.asMutableStateFLow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: AppUseCase) : ViewModel() {
    private val _responsePopularMovie: MutableStateFlow<UiState<DataPopularMovie>> =
        MutableStateFlow(UiState.Empty)
    val responsePopularMovie = _responsePopularMovie.asStateFlow()

    private val _responseNowPlaying: MutableStateFlow<UiState<DataNowPlaying>> =
        MutableStateFlow(UiState.Empty)
    val responseNowPlaying = _responseNowPlaying.asStateFlow()

    private val _responseDetail: MutableStateFlow<UiState<DataDetailMovie>> =
        MutableStateFlow(UiState.Empty)
    val responseDetail = _responseDetail.asStateFlow()



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

    fun fetchDetail(movieId: Int) {
        viewModelScope.launch {
            _responseDetail.asMutableStateFLow {
                useCase.fetchDetailMovie(movieId)
            }
        }
    }
}