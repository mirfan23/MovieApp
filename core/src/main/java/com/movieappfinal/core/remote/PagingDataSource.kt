package com.movieappfinal.core.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.remote.services.ApiEndPoint
import com.movieappfinal.core.utils.DataMapper.toSearchData

class PagingDataSource(
    private val apiEndPoint: ApiEndPoint,
    private val query: String
) : PagingSource<Int, DataSearchMovie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataSearchMovie> {
        return try {
            val position = params.key ?: 1
            val response = apiEndPoint.fetchSearch(query, position)
            val movies = response.results.map { it.toSearchData() }.toList()
            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataSearchMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
