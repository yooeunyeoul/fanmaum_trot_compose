package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerState(
    val service: String,
    val isAvailable: Boolean = true
)
