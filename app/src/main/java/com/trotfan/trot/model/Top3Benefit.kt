package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Top3Benefit(
    val content: String = "",
    @SerialName("ended_at")
    val endedAt: String = "",
    val id: Int = -1,
    @SerialName("started_at")
    val startedAt: String = "",
    val title: String = ""
)