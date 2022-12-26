package com.trotfan.trot.network.impl

import com.trotfan.trot.model.DatePickerRange
import com.trotfan.trot.model.StarRankingDetail
import com.trotfan.trot.model.StarRankingList
import com.trotfan.trot.network.HttpRoutes.DATE_PICKER
import com.trotfan.trot.network.HttpRoutes.RANK
import com.trotfan.trot.network.RankingHistoryService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RankinHistoryServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : RankingHistoryService {
    override suspend fun getMonthlyStarList(
        year: String,
        month: String
    ): CommonResponse<StarRankingList> {
        val response = httpClient.get("$RANK/monthly") {
            contentType(ContentType.Application.Json)
            url {
                parameter("month", month)
                parameter("year", year)
            }
        }
        return response.body()
    }

    override suspend fun getDailyStarList(
        year: String,
        month: String,
        day: String
    ): CommonResponse<StarRankingList> {
        return httpClient.get {
            url("$RANK/daily")
        }.body()
    }

    override suspend fun getStarRankingDetail(
        starId: Int
    ): CommonResponse<List<StarRankingDetail>> {
        return httpClient.get {
            url("$RANK/monthly/detail") {
                parameter("star_id", starId)
            }
        }.body()
    }

    override suspend fun getDatePickerRange(): CommonResponse<DatePickerRange> {
        return httpClient.get {
            url("$DATE_PICKER/rank")
        }.body()
    }
}