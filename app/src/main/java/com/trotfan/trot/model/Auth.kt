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
data class UserToken(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("refresh_token")
    val refresh_token: String?,
    @SerializedName("id")
    val id: Int
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
    @SerializedName("idp")
    val idp: Int,
    @SerializedName("sub")
    val sub: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("invitation_code")
    val invitation_code: String?,
    @SerializedName("star_id")
    val star_id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_number")
    val phone_number: String?,
    @SerializedName("redeem_code")
    val redeem_code: String?
)