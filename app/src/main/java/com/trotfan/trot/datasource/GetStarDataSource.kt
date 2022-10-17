package com.trotfan.trot.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trotfan.trot.model.FavoriteStar
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.ui.components.input.SearchStatus
import retrofit2.HttpException
import javax.inject.Inject

class GetStarDataSource @Inject constructor(
    private val service: SignUpService
) : PagingSource<String, FavoriteStar>() {

    var starName: String = ""

    override fun getRefreshKey(state: PagingState<String, FavoriteStar>): String? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, FavoriteStar> {
        return try {

            val data = service.getStarList(params.key ?: "", search = starName).data
            Log.e("cursor", "nextCursor::::" + params.key.toString())
            val starList = data.stars
            var nextCursor = data.meta.nextCursor
            var prevCursor = data.meta.prevCursor

            if (starList.isEmpty() && nextCursor.isEmpty() && prevCursor.isEmpty()) {
                LoadResult.Error(Exception(SearchStatus.NoResult.name))
            } else {
                LoadResult.Page(
                    data = starList,
                    prevKey = if (prevCursor.isEmpty()) null else prevCursor,
                    nextKey = if (nextCursor.isEmpty()) null else nextCursor
                )
            }
        } catch (e: HttpException) {
            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}