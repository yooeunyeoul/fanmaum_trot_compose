package com.trotfan.trot.network

import com.trotfan.trot.BuildConfig

object HttpRoutes {
    //    private const val BASE_URL = "https://fanwoori.ap.ngrok.io"
    val BASE_URL = "https://dev.fanmaum.ap.ngrok.io"
    val LOCAL_URL = "https://local.fanmaum.ap.ngrok.io"

    val KAKAO_LOGIN = "$LOCAL_URL/auth/login/kakao"
    val GOOGLE_LOGIN = "$LOCAL_URL/auth/login/google"
    val APPLE_LOGIN = "$BASE_URL/auth/login/apple"
    val SERVER_STATE = "https://fanwoori-develop-api.services"
    val GET_STAR_LIST = "$BASE_URL/stars"
    val USERS = "$LOCAL_URL/users"
    val SMS = "$BASE_URL/auth/sms"
    val VOTE = "$BASE_URL/votes"
    val POPUPS = "$BASE_URL/popups"
    val RANK = "$BASE_URL/rank"
    val DATE_PICKER = "$BASE_URL/datepickers"
    val BANNER = "$BASE_URL/banners"
    val LOGOUT = "$LOCAL_URL/auth/logout"
    val GOOGLE_PURCHASE = "$LOCAL_URL/google-purchase"
}