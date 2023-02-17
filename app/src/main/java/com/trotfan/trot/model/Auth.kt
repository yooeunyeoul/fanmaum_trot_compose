package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoTokens(
    val refresh_token: String? = null,
    val access_token: String? = null,
    val id_token: String? = null
)

@Serializable
data class GoogleToken(
    val auth_code: String? = null
)

@Serializable
data class AppleToken(
    val id_token: String? = null,
    val access_token: String? = null,
    val refresh_token: String? = null
)

@Serializable
data class UserToken(
    @SerializedName("token")
    val token: String
)

@Serializable
data class UserInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("idp")
    val idp: Int,
    @SerializedName("invitation_code")
    val invitation_code: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("star")
    val star: Star?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_number")
    val phone_number: String?,
    @SerializedName("redeem_code")
    val redeemed_code: Boolean?,
    @SerializedName("agrees_terms")
    val agrees_terms: Boolean?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("total_used_votes")
    val total_used_votes: Int
)