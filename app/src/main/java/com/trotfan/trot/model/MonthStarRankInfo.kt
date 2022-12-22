package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthStarRankInfo(
    val men: List<MonthStarRank>?,
    val women: List<MonthStarRank>?
)

@Serializable
data class MonthStarRank(
    val gender: Int?,
    val image: String?,
    val name: String?,
    val rank: Int?,
    val score: Int?,
    @SerialName("star_id")
    val starId: Int?
)