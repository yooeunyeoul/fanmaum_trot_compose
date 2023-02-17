package com.trotfan.trot.network.impl

import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.Ticket
import com.trotfan.trot.model.TicketItem
import com.trotfan.trot.model.VoteInfo
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject


class VoteServiceImpl @Inject constructor(private val httpClient: HttpClient) : VoteService {
    override suspend fun vote(): CommonResponse<VoteInfo> {
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

    override suspend fun voteTickets(userId: Long, token: String): CommonResponse<Ticket> {
        val response = httpClient.get(HttpRoutes.USERS + "/${userId}/tickets/total") {
            header(
                "Authorization",
                "Bearer $token"
            )
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }


}
