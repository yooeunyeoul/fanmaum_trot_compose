package com.trotfan.trot.model


import kotlinx.serialization.Serializable

@Serializable
data class ReturnStatus(
    val status: Status
)