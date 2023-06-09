package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class StarsList<T>(
    val meta: Meta,
    val stars: List<T>
)

@Serializable
data class VoteMainStars(
    val men: List<VoteMainStar>? = listOf(),
    val women: List<VoteMainStar>? = listOf()
)

@Serializable
data class Star(
    val id: Int,
    val image: String,
    val name: String,
    val gender: Int
)

@Serializable
data class VoteMainStar(
    val id: Int? = null,
    val image: String? = null,
    val name: String? = null,
    var rank: Int? = null,
    var votes: Int? = null
)

@Serializable
data class FavoriteStarInfo(
    val id: Int? = null,
    val image: String? = null,
    val name: String? = null,
    var rank: Rank? = null,
    val votes: Int? = null
) {
    @Serializable
    data class Rank(
        var daily: Int? = null,
        val monthly: Int? = null
    )
}




