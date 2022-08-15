package com.trotfan.trot.model
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val nextCursor: String,
    val path: String,
    val perPage: Int,
    val prevCursor: String?
)