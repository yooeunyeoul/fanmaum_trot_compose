package com.trotfan.trot.network.impl

import com.trotfan.trot.model.SmsCertificationRequestResult
import com.trotfan.trot.network.SignUpService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import javax.inject.Inject

enum class SmsHeaderKey(val key: String, url: String) {
    DEV(
        key = "MS0xMzY1NjY2MTAyNDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZQ==",
        url = "https://api.apistore.co.kr/ppurio_test/1/message_test/sms/fanmaum"
    ),
    PRO(
        key = "ODgyOS0xNTMxNzA4OTA3MTM5LTk5MmY1MTRkLWRmNmUtNDg3OC1hZjUxLTRkZGY2ZTg4NzgyYg==",
        url = "https://api.apistore.co.kr/ppurio/1/message/sms/fanmaum"
    )

}

class SignUpServiceImpl @Inject constructor(private val httpClient: HttpClient) : SignUpService {
    override suspend fun requestCertificationCode(phoneNumber: String, message: String): SmsCertificationRequestResult =
        httpClient.post("https://api.apistore.co.kr/ppurio_test/1/message_test/sms/fanmaum") {
            headers {
                append("x-waple-authorization", SmsHeaderKey.DEV.key)
            }
            body = FormDataContent(Parameters.build {
                append("send_phone", "07050564520")
                append("dest_phone", phoneNumber)
                append("msg_body", message)
            })
        }


}
