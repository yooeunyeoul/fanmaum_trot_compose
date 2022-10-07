package com.trotfan.trot.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("next_cursor")
    val nextCursor: String,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("prev_cursor")
    val prevCursor: String
)