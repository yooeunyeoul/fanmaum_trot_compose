package com.trotfan.trot.model
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class VoteData(
    val quantity: Int,
    @SerialName("star_name")
    val star_name: String,
    @SerialName("user_name")
    val user_name: String
)