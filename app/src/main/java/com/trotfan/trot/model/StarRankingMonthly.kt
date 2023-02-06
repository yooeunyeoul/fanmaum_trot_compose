package com.trotfan.trot.model

import kotlinx.serialization.Serializable


@Serializable
data class StarRankingList(
    val men: List<StarRanking> = listOf(),
    val women: List<StarRanking> = listOf()
)

@Serializable
data class StarRanking(
    val score: Int = 0,
    val id: Int = 0,
    val gender: Int = 0,
    val name: String = "",
    val image: String = "",
    val rank: Int = 0
)

@Serializable
data class StarRankingDetail(
    val day: String = "",
    val rank: Int = 0,
    val point: Int = 0,
    val votes: Int = 0
)
