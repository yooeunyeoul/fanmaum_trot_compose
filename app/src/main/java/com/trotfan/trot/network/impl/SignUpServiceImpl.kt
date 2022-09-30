package com.trotfan.trot.network.impl

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
import javax.inject.Inject


class SignUpServiceImpl @Inject constructor(private val httpClient: HttpClient) : SignUpService {
    @OptIn(InternalAPI::class)
    override suspend fun requestCertificationCode(
        phoneNumber: String
    ): ReturnStatus {
        return httpClient.submitForm(
            url = HttpRoutes.SMS
        ) {
            body = FormDataContent(Parameters.build {
                append("to", phoneNumber)
            })
        }.body()
    }

    override suspend fun getStarList(cursor: String, search: String): CommonResponse<stars<Star>> {

        val response = httpClient.get(HttpRoutes.GET_STAR_LIST) {
            contentType(ContentType.Application.Json)
            url {
                parameter("cursor", cursor)
                parameter("search", search)
            }
        }
        return response.body()
    }

    override suspend fun starSearch(page: String, search: String): CommonResponse<stars<Star>> {

        val response = httpClient.get(HttpRoutes.GET_STAR_LIST) {
            contentType(ContentType.Application.Json)
            url {
                parameter("page", page)
                parameter("search", search)
            }
        }
        return response.body()
    }

    override suspend fun updateUser(
        userId: String,
        nickName: String?,
        starId: String?,
        phoneNumber: String?,
        redeemCode: String?
    ): UpdateUserResponse {
        val response = httpClient.request(HttpRoutes.USERS + "/${userId}") {
            method = HttpMethod.Patch
            contentType(ContentType.Application.Json)
            headers {
                append(
                    name = "X-Requested-With",
                    value = "XMLHttpRequest"
                )
            }
            setBody(FormDataContent(Parameters.build {
                if (nickName != null) {
                    append("name", nickName)
                }
                if (starId != null) {
                    append("star_id", starId)
                }
                if (phoneNumber != null) {
                    append("phone_number", phoneNumber)
                }
                if (redeemCode != null) {
                    append("redeem_code", redeemCode)
                }
            }))

        }
        return when (response.status.value) {
            200 -> {
                UpdateUserResponse(code = 200)
            }
            else -> {
                response.body()
            }
        }
    }
}
