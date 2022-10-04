package com.trotfan.trot.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val code: Int,
    val message: String
)