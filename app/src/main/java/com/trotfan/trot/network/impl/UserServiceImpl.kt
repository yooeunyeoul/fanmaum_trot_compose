package com.trotfan.trot.network.impl

import com.trotfan.trot.model.ProfileImage
import com.trotfan.trot.model.TicketsHistory
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.UserService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.*
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.*
import io.ktor.util.*
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@OptIn(InternalAPI::class)
class UserServiceImpl @Inject constructor(private val httpClient: HttpClient) : UserService {
    override suspend fun updateUser(
        userId: Long,
        nickName: String?,
        starId: Int?,
        phoneNumber: String?,
        redeemCode: String?,
        agreesTerms: Boolean?,
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

            agreesTerms?.let {
                json.put("agrees_terms", it)
            }
            body = (json.toString())
        }.body()
    }

    override suspend fun userProfileUpload(
        token: String,
        userId: Long,
        image: File
    ): CommonResponse<ProfileImage> {
        return httpClient.post(HttpRoutes.USERS + "/${userId}/images") {
            contentType(ContentType.Image.JPEG)
            header(
                "Authorization",
                "Bearer $token"
            )
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("image", image.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpg")
                            append(HttpHeaders.ContentDisposition, "filename=${image.path}")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }.body()
    }

    override suspend fun userTicketHistory(
        cursor: String,
        token: String,
        userId: Long,
        filter: String?
    ): CommonResponse<TicketsHistory> {
        return httpClient.get(HttpRoutes.USERS + "/${userId}/tickets") {
            header(
                "Authorization",
                "Bearer $token"
            )
            url {
                parameter("cursor", cursor)
                filter?.let {
                    parameter("filter", filter)
                }
            }
            contentType(ContentType.Application.Json)
        }.body()
    }
}