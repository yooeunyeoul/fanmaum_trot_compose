package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class MainPopups(
    val update: Update?,
    val layers: List<Layer>,
    val is_rewarded: Boolean
)

@Serializable
data class Update(
    val title: String,
    val content: String
)

@Serializable
data class Layer(
    val order: Int,
    val image: String
)
