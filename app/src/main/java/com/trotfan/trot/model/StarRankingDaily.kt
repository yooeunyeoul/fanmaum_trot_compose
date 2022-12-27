package com.trotfan.trot.model

import kotlinx.serialization.Serializable


@Serializable
data class StarRankingDailyList(
    val men: List<StarRankingDaily> = listOf(),
    val women: List<StarRankingDaily> = listOf()
)

@Serializable
data class StarRankingDaily(
    val name: String = "",
    val image: String = "",
    val score: Int = 0,
    val rank: Int = 0,
    val gender: String = "",
    val quantity: Int = 0
)
