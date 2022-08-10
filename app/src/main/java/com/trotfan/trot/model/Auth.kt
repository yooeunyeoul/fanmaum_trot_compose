package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoTokens(
    val refreshToken: String? = null,
    val accessToken: String? = null,
    val idToken: String? = null
)

@Serializable
data class GoogleToken(
    val authCode: String? = null
)

@Serializable
data class UserInfo(
    val access_token: String,
    val refresh_token: String?
)
