package com.trotfan.trot.network.impl

import com.trotfan.trot.model.MissionState
import com.trotfan.trot.network.ChargeService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class ChargeServiceImpl @Inject constructor(private val httpClient: HttpClient) : ChargeService {

    override suspend fun certificationCharge(
        userToken: String,
        userId: Int,
        productId: String,
        purchaseToken: String,
        packageName: String
    ): CommonResponse<Unit> {

        val response = httpClient.submitForm(
            url = HttpRoutes.GOOGLE_PURCHASE
        ) {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $userToken"
            )
            body = FormDataContent(Parameters.build {
                append("user_id", userId.toString())
                append("product_id", productId)
                append("purchase_token", purchaseToken)
                append("package_name", packageName)
            })
        }
        return response.body()

    }

    override suspend fun getMissions(userToken: String): CommonResponse<MissionState> {
        return httpClient.get(HttpRoutes.MISSIONS) {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $userToken"
            )
        }.body()
    }
}
