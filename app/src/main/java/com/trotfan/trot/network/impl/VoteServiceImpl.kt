package com.trotfan.trot.network.impl

import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject


class VoteServiceImpl @Inject constructor(private val httpClient: HttpClient) : VoteService {
    override suspend fun vote(): CommonResponse<Top3Benefit> {
        val response = httpClient.get(HttpRoutes.VOTE) {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    override suspend fun starRank(starId: Int): CommonResponse<FavoriteStarInfo> {
        val response = httpClient.get(HttpRoutes.GET_STAR_LIST + "/${starId}/rank") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}
