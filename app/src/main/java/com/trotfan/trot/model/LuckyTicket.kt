package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LuckyTicket(
    val chance: Int,
    @SerialName("last_time")
    val lastTime: String,
    @SerialName("roulette_quantity")
    val rouletteQuantity: Int,
    @SerialName("user_id")
    val userId: Int
)