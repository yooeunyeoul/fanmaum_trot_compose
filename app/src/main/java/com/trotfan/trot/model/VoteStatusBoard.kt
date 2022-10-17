package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteStatusBoard(
    val quantity: Int,
    @SerialName("star_name")
    val star_name: String,
    @SerialName("user_name")
    val user_name: String
)