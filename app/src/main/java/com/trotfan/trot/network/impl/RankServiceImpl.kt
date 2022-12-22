package com.trotfan.trot.network.impl

import com.trotfan.trot.model.MonthStarRankInfo
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.RankService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject


class RankServiceImpl @Inject constructor(private val httpClient: HttpClient) : RankService {

    override suspend fun monthStarRank(month: Int): CommonResponse<MonthStarRankInfo> {
        val response = httpClient.get(HttpRoutes.RANK + "/monthly") {
            contentType(ContentType.Application.Json)
            url {
                parameter("month", month)
            }
        }
        return response.body()
    }
}
