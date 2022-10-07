package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsAuth(
    val code: Int,
    @SerialName("created_at")
    val createdAt: String,
    val signature: String,
    val time: Long
)