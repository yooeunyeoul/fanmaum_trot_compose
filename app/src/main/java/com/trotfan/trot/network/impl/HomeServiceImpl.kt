package com.trotfan.trot.network.impl

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.model.VoteTicket
import com.trotfan.trot.network.HomeService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.json.JSONObject
import javax.inject.Inject

class HomeServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : HomeService {
    override suspend fun getMainPopups(token: String): CommonResponse<MainPopups> {
        return httpClient.get {
            url(HttpRoutes.POPUPS)
            header(
                "Authorization",
                "Bearer $token"
            )
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postVoteTicket(
        voteTicket: VoteTicket,
        token: String
    ): CommonResponse<Unit> {
        return httpClient.post {
            url("${HttpRoutes.VOTE}/${voteTicket.vote_id}/ticket")
            header(
                "Authorization",
                "Bearer $token"
            )
            contentType(ContentType.Application.Json)
            val json = JSONObject()
            json.put("star_id", voteTicket.star_id.toString())
            json.put("quantity", voteTicket.quantity.toString())
            body = (json.toString())
        }.body()
    }
}