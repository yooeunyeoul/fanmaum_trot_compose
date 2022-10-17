package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsAuth(
    val code: Int,
    val created_at: String,
    val signature: String,
    val time: Long
)