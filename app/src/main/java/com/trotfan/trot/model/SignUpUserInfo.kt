package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpUserInfo(
    val id: Int,
    @SerialName("invitation_code")
    val invitationCode: String,
    val name: String,
    @SerialName("redeem_code")
    val redeemCode: String,
    @SerialName("star_id")
    val starId: Int
)