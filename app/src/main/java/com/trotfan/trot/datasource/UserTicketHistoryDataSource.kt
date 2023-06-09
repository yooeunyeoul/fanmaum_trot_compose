package com.trotfan.trot.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.model.FavoriteStar
import com.trotfan.trot.model.TicketItem
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.UserService
import com.trotfan.trot.ui.components.input.SearchStatus
import retrofit2.HttpException
import javax.inject.Inject

class UserTicketHistoryDataSource @Inject constructor(
    private val service: UserService,
    private val loadingHelper: LoadingHelper,
) : PagingSource<String, TicketItem>() {

    var token: String = ""
    var userId: Long = 0
    var filter: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TicketItem> {
        return try {
            loadingHelper.showProgress()
            val data =
                service.userTicketHistory(
                    params.key ?: "",
                    token = token,
                    userId = userId,
                    filter = filter
                ).data
            Log.e("cursor", "nextCursor::::" + params.key.toString())
            val ticketsHistory = data?.tickets
            val nextCursor = data?.meta?.nextCursor
            val prevCursor = data?.meta?.prevCursor
            loadingHelper.hideProgress()
            if (ticketsHistory?.isEmpty() == true && nextCursor?.isEmpty() == true && prevCursor?.isEmpty() == true) {
                LoadResult.Error(Exception(SearchStatus.NoResult.name))

            } else {
                LoadResult.Page(
                    data = ticketsHistory ?: listOf(),
                    prevKey = if (prevCursor?.isEmpty() == true) null else prevCursor,
                    nextKey = if (nextCursor?.isEmpty() == true) null else nextCursor
                )
            }

        } catch (e: HttpException) {
            loadingHelper.hideProgress()
            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: Exception) {
            loadingHelper.hideProgress()
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }

    override fun getRefreshKey(state: PagingState<String, TicketItem>): String? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }
}