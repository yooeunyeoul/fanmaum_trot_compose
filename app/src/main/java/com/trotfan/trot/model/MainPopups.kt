package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class MainPopups(
    val update: Update?,
    val layers: List<Layer>?,
    val is_rewarded: Boolean,
    val autoVote: AutoVote
)

@Serializable
data class Update(
    val version: String,
    val title: String,
    val content: String
)

@Serializable
data class Layer(
    val order: Int,
    val image: String
)

@Serializable
data class AutoVote(
    val is_available: Boolean,
    val star: Star,
    val quantity: Int
)
