package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TicketsHistory(
    val tickets: List<TicketItem>,
    val meta: Meta
)

@Serializable
data class TicketItem(
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("filter")
    val filter: String,
    @SerializedName("title")
    val title: String
)