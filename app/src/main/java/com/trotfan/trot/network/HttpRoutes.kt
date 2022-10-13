package com.trotfan.trot.network

object HttpRoutes {
    private const val BASE_URL = "https://fanwoori.ap.ngrok.io"
    const val KAKAO_LOGIN = "$BASE_URL/oauth2/login/kakao"
    const val GOOGLE_LOGIN = "$BASE_URL/oauth2/login/google"
    const val APPLE_LOGIN = "$BASE_URL/oauth2/login/apple"
    const val SERVER_STATE = "https://fanwoori-develop-api.services"
    const val STAR_SEARCH = "$BASE_URL/com.trotfan.trot.model.stars"
    const val GET_STAR_LIST = "$BASE_URL/stars"
    const val USERS = "$BASE_URL/users"
    const val SMS = "$BASE_URL/auth/sms"
    const val VOTE_LIST = "$BASE_URL/votes"
}