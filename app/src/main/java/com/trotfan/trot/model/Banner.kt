package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    val image: String,
    val path: String,
    val title: String,
    val type: String
)