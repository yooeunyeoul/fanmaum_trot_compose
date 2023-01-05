package com.trotfan.trot.network.impl

import com.trotfan.trot.model.*
import com.trotfan.trot.network.HttpRoutes.DATE_PICKER
import com.trotfan.trot.network.HttpRoutes.BANNER
import com.trotfan.trot.network.HttpRoutes.RANK
import com.trotfan.trot.network.RankingHistoryService
import com.trotfan.trot.network.response.CommonListResponse
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
        year: String?,
        month: String?
    ): CommonResponse<StarRankingList> {
        val response = httpClient.get("$RANK/monthly") {
            contentType(ContentType.Application.Json)
            url {
                year?.let {
                    parameter("year", year)
                }
                month?.let {
                    parameter("month", it)
                }
            }
        }
        return response.body()
    }

    override suspend fun getDailyStarList(
        year: String,
        month: String,
        day: String
    ): CommonResponse<StarRankingDailyList> {
        return httpClient.get("$RANK/daily") {
            contentType(ContentType.Application.Json)
            url {
                parameter("year", year)
                parameter("month", month)
                parameter("day", day)
            }
        }.body()
    }

    override suspend fun getStarRankingDetail(
        starId: Int,
        year: String?,
        month: String?
    ): CommonResponse<List<StarRankingDetail>> {
        return httpClient.get("$RANK/monthly/detail") {
            url {
                parameter("star_id", starId)
                if (!year.isNullOrEmpty()) {
                    parameter("year", year)
                }
                if (!month.isNullOrEmpty()) {
                    parameter("month", month)
                }
            }
        }.body()
    }

    override suspend fun getDatePickerRange(): CommonResponse<DatePickerRange> {
        return httpClient.get {
            url("$DATE_PICKER/rank")
        }.body()
    }

    override suspend fun getBanners(
        group: String,
        platform: String
    ): CommonListResponse<Banner> {
        val response = httpClient.get(BANNER) {
            contentType(ContentType.Application.Json)
            url {
                parameter("group", group)
                parameter("platform", platform)
            }
        }
        return response.body()
    }
}