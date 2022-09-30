package com.trotfan.trot.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trotfan.trot.model.Star
import com.trotfan.trot.network.SignUpService
import retrofit2.HttpException
import javax.inject.Inject

class GetStarDataSource @Inject constructor(
    private val service: SignUpService
) : PagingSource<String, Star>() {

    var starName: String = ""

    override fun getRefreshKey(state: PagingState<String, Star>): String? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Star> {
        return try {

            val data = service.getStarList(params.key ?: "", search = starName).data
            Log.e("cursor", "nextCursor::::" + params.key.toString())
            val starList = data.stars
            var nextCursor = data.meta.nextCursor
            var prevCursor = data.meta.prevCursor

            LoadResult.Page(
                data = starList,
                prevKey = if (prevCursor?.isEmpty() == true) null else prevCursor,
                nextKey = if (nextCursor?.isEmpty() == true) null else nextCursor
            )

        } catch (e: HttpException) {
            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}

//class GetStarDataSourceForName @Inject constructor(
//    private val service: SignUpService
//) : GetStarDataSource(service) {
//
//    var starName: String = ""
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, Star> {
//        return try {
//            val nextPage = ""
//            var nextCursor: String? = null
//            var prevCursor: String? = null
//            val data = service.starSearch(nextCursor ?: "", name = starName).data
//            val starList = data.stars
//            nextCursor = data.meta.nextCursor
//            prevCursor = data.meta.prevCursor
//
//            if (starList.isEmpty()) {
//                LoadResult.Error(Exception("No com.trotfan.trot.model.Result Error"))
//            } else {
//                LoadResult.Page(
//                    data = starList,
//                    prevKey = if (prevCursor?.isEmpty() == true) null else prevCursor,
//                    nextKey = if (nextCursor?.isEmpty() == true) null else nextCursor
//                )
//            }
//
//        } catch (e: HttpException) {
//            LoadResult.Error(Exception(e.localizedMessage))
//        } catch (e: Exception) {
//            LoadResult.Error(Exception(e.localizedMessage))
//        }
//    }
//}