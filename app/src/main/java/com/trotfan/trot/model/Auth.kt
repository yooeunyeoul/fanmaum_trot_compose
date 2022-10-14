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
data class AppleToken(
    val idToken: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)

@Serializable
data class UserToken(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("refresh_token")
    val refresh_token: String?,
    @SerializedName("user_id")
    val user_id: Int
)

@Serializable
data class UserInfoData(
    @SerializedName("data")
    val data: UserInfo
)

@Serializable
data class UserInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("invitation_code")
    val invitation_code: String?,
    @SerializedName("star")
    val favoriteStar: FavoriteStar?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_number")
    val phone_number: String?,
    @SerializedName("redeem_code")
    val redeem_code: String?
)