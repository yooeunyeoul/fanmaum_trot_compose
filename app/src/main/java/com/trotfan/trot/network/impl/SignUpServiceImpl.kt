package com.trotfan.trot.network.impl

import com.trotfan.trot.BuildConfig
import com.trotfan.trot.model.SmsCertificationRequestResult
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
                append("send_phone", "07050564520")
                append("dest_phone", phoneNumber)
                append("msg_body", message)
            })
        }.body()
    }
}
