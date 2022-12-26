package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteInfo(
    val id: Int = -1,
    val title: String = "",
    val content: String = "",
    @SerialName("started_at")
    val startedAt: String = "",
    @SerialName("ended_at")
    val endedAt: String = "",
    @SerialName("stars")
    val voteMainStars: VoteMainStars ?=null,
)