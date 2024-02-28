package com.movieappfinal.core.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.remote.data.DetailMovieResponse
import com.movieappfinal.core.remote.data.NowPlayingResponse
import com.movieappfinal.core.remote.data.PopularMovieResponse
import com.movieappfinal.core.remote.data.TrendingMovieResponse
import com.movieappfinal.core.remote.services.ApiEndPoint
import com.movieappfinal.core.utils.safeApiCall
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val apiEndPoint: ApiEndPoint) {
    suspend fun fetchPopularMovie(): PopularMovieResponse {
        return safeApiCall { apiEndPoint.fetchPopularMovie() }
    }

    suspend fun fetchNowPlayingMovie(): NowPlayingResponse {
        return safeApiCall { apiEndPoint.fetchNowPlayingMovie() }
    }

    suspend fun fetchTrendingMovie(): TrendingMovieResponse {
        return safeApiCall { apiEndPoint.fetchTrendingMovie() }
    }

    suspend fun fetchDetailMovie(
        id: Int? = 0
    ): DetailMovieResponse {
        return safeApiCall {
            apiEndPoint.fetchDetailMovie(
                id
            )
        }
    }

    fun fetchSearch(query: String): Flow<PagingData<DataSearchMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = { PagingDataSource(apiEndPoint, query) }
        ).flow
    }
}