package com.trotfan.trot.network.impl

import com.trotfan.trot.model.Result
import com.trotfan.trot.network.ChargeService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import org.json.JSONObject
import javax.inject.Inject

@OptIn(InternalAPI::class)
class ChargeServiceImpl @Inject constructor(private val httpClient: HttpClient) : ChargeService {


    override suspend fun certificationCharge(
        userId: Int,
        productId: String,
        purchaseToken: String
    ): CommonResponse<Unit> {
        try {
            val response = httpClient.post {
                url(HttpRoutes.GOOGLE_PURCHASE)
                contentType(ContentType.Application.Json)
                val json = JSONObject()
                json.put("user_id", userId)
                json.put("product_id", productId)
                json.put("purchase_token", purchaseToken)
                body = (json.toString())
            }

            return response.body<CommonResponse<Unit>>()
        } catch (e: java.lang.Exception) {
            return CommonResponse(data = null, result = Result(code = 200, message = ""))


        }


    }
}
