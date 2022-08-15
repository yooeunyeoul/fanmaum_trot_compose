package com.trotfan.trot.network

object HttpRoutes {
    private const val BASE_URL = "https://fanwoori.ap.ngrok.io"
    const val KAKAO_LOGIN = "$BASE_URL/oauth2/login/kakao"
    const val GOOGLE_LOGIN = "$BASE_URL/oauth2/login/google"
    const val SERVER_STATE = "https://fanwoori-develop-api.services"
    const val USER = "$BASE_URL/users"

    enum class SmsCertification(val key: String, val url: String) {
        DEV(
            key = "MS0xMzY1NjY2MTAyNDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZQ==",
            url = "https://api.apistore.co.kr/ppurio_test/1/message_test/sms/fanmaum"
        ),
        PRO(
            key = "ODgyOS0xNTMxNzA4OTA3MTM5LTk5MmY1MTRkLWRmNmUtNDg3OC1hZjUxLTRkZGY2ZTg4NzgyYg==",
            url = "https://api.apistore.co.kr/ppurio/1/message/sms/fanmaum"
        )
    }
}