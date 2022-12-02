package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class Tickets(
    val expired: Expired? = null,
    val unlimitedList: List<Unlimited>? = null
)

@Serializable
data class Expired(
    val today: Int? = 0,
    val unlimited: Int? = 0
)

@Serializable
data class Unlimited(
    val createdAt: String,
    val expiredAt: String?,
    val id: Int,
    val quantity: Int,
    val statusId: Int,
    val typeId: Int,
    val userId: Int
)
