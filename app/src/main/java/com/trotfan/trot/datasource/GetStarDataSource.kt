package com.trotfan.trot.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trotfan.trot.model.Person
import com.trotfan.trot.model.StarItem
import com.trotfan.trot.network.SignUpService
import retrofit2.HttpException
import javax.inject.Inject

open class GetStarDataSource @Inject constructor(
    private val service: SignUpService
) : PagingSource<Int, Person>() {
    override fun getRefreshKey(state: PagingState<Int, Person>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val nextPage = params.key ?: 1
            val starList = service.getStarList(page = nextPage)

            LoadResult.Page(
                data = starList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (starList.isEmpty()) null else nextPage + 1
            )

        } catch (e: HttpException) {
            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}

class GetStarDataSourceForName @Inject constructor(
    private val service: SignUpService
) : GetStarDataSource(service) {

    var starName: String = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val nextPage = params.key ?: 1
            val starList = service.starSearch(page = nextPage, name = starName)

            if (starList.isEmpty() && nextPage == 1) {
                LoadResult.Error(Exception("No Result Error"))
            } else {
                LoadResult.Page(
                    data = starList,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (starList.isEmpty()) null else nextPage + 1
                )
            }

        } catch (e: HttpException) {
            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}