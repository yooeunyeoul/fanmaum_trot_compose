package com.trotfan.trot.network

object HttpRoutes {
    //    private const val BASE_URL = "https://fanwoori.ap.ngrok.io"
    val BASE_URL = "https://dev.fanmaum.ap.ngrok.io"
    val LOCAL_URL = "https://local.fanmaum.ap.ngrok.io"
    val PRODUCT_URL = "https://api.fanmaum.com"

    val KAKAO_LOGIN = "$PRODUCT_URL/auth/login/kakao"
    val GOOGLE_LOGIN = "$PRODUCT_URL/auth/login/google"
    val APPLE_LOGIN = "$PRODUCT_URL/auth/login/apple"
    val SERVER_STATE = "https://fanwoori-develop-api.services"
    val GET_STAR_LIST = "$PRODUCT_URL/stars"
    val USERS = "$PRODUCT_URL/users"
    val SMS = "$PRODUCT_URL/auth/sms"
    val VOTE = "$PRODUCT_URL/votes"
    val POPUPS = "$PRODUCT_URL/popups"
    val RANK = "$PRODUCT_URL/rank"
    val DATE_PICKER = "$PRODUCT_URL/datepickers"
    val BANNER = "$PRODUCT_URL/banners"
    val LOGOUT = "$PRODUCT_URL/auth/logout"
    val GOOGLE_PURCHASE = "$PRODUCT_URL/google-purchase"
    val MISSIONS = "$PRODUCT_URL/charges/free"
    val ROULETTE = "$PRODUCT_URL/charges/roulette"
}