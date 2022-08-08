package com.trotfan.trot.network

object HttpRoutes {
    private const val BASE_URL = "https://fanwoori.ap.ngrok.io"
    const val KAKAO_LOGIN = "$BASE_URL/oauth2/login/kakao"
    const val GOOGLE_LOGIN = "$BASE_URL/oauth2/login/google"
}