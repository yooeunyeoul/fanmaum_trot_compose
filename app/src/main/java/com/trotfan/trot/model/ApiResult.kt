package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
class ApiResult(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)