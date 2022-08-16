package com.trotfan.trot.network.impl

import com.trotfan.trot.BuildConfig
import com.trotfan.trot.model.*
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.SignUpService
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
        phoneNumber: String,
        message: String
    ): SmsCertificationRequestResult {
        return httpClient.submitForm(
            url = if (BuildConfig.DEBUG) {
                HttpRoutes.SmsCertification.DEV.url
            } else {
                HttpRoutes.SmsCertification.PRO.url
            }
        ) {
            headers {
                append(
                    name = "x-waple-authorization",
                    value = if (BuildConfig.DEBUG) {
                        HttpRoutes.SmsCertification.DEV.key
                    } else {
                        HttpRoutes.SmsCertification.PRO.key
                    }
                )
            }
            body = FormDataContent(Parameters.build {
                append("send_phone", "16440219")
                append("dest_phone", phoneNumber)
                append("msg_body", message)
            })
        }.body()
    }

    override suspend fun getStarList(page: Int): List<Person> {

        val response = httpClient.get(HttpRoutes.GET_STAR_LIST) {
            contentType(ContentType.Application.Json)
            url {
                parameter("page", page)
            }
        }
        return response.body<StarItem>().data
    }

    override suspend fun starSearch(page: Int, name: String): List<Person> {

        val response = httpClient.get(HttpRoutes.GET_STAR_LIST + "/${name.trim()}") {
            contentType(ContentType.Application.Json)
            url {
                parameter("page", page)
            }
        }
        return response.body<StarItem>().data
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
