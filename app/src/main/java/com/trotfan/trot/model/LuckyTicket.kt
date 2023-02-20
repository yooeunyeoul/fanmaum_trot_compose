package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class LuckyTicket(
    val next_reward: Int,
    val rewarded_at: String?,
    val today: Today,

    ) {
    @Serializable
    data class Today(
        val max: Int,
        val remaining: Int
    )
}
