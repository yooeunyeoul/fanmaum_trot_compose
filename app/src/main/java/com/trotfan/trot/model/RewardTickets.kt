package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class RewardTickets(
    @SerializedName("rewarded_at")
    val rewarded_at: String,
    @SerializedName("today")
    val today: Today,
    @SerializedName("tickets")
    val tickets: RewardTicket
)

@kotlinx.serialization.Serializable
data class Today(
    @SerializedName("remaining")
    val remaining: Int
)

@kotlinx.serialization.Serializable
data class RewardTicket(
    @SerializedName("unlimited")
    val unlimited: Long,
    @SerializedName("limited")
    val limited: Long
)