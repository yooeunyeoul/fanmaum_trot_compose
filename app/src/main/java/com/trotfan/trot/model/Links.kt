package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val first: String?,
    val last: String?,
    val next: String,
    val prev: String?
)