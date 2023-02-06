package com.trotfan.trot.network.impl

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.trotfan.trot.model.*
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.json.JSONObject
import javax.inject.Inject


class SignUpServiceImpl @Inject constructor(private val httpClient: HttpClient) : SignUpService {
    @OptIn(InternalAPI::class)
    override suspend fun requestCertificationCode(
        phoneNumber: String
    ): CommonResponse<SmsAuth> {
        return httpClient.post {
            url(HttpRoutes.SMS)
            contentType(ContentType.Application.Json)
            val json = JSONObject()
            json.put("to", phoneNumber)
            body = (json.toString())
        }.body()
    }

    override suspend fun getStarList(
        cursor: String,
        search: String
    ): CommonResponse<StarsList<FavoriteStar>> {

        val response = httpClient.get(HttpRoutes.GET_STAR_LIST) {
            contentType(ContentType.Application.Json)
            url {
                parameter("cursor", cursor)
                parameter("search", search)
            }
        }
        return response.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateUser(
        userId: Long,
        nickName: String?,
        starId: Int?,
        phoneNumber: String?,
        redeemCode: String?,
        agrees_terms: Boolean?,
        token: String
    ): CommonResponse<Unit> {
        return httpClient.patch(HttpRoutes.USERS + "/${userId}") {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $token"
            )
            val json = JSONObject()
            if (nickName != null) {
                json.put("name", nickName)
            }
            if (starId != null) {
                json.put("star_id", starId.toString())
            }
            if (phoneNumber != null) {
                json.put("phone_number", phoneNumber)
            }
            if (redeemCode != null) {
                json.put("redeem_code", redeemCode)
            }

            agrees_terms?.let {
                json.put("agrees_terms", it)
            }
            body = (json.toString())
        }.body()
    }
}

