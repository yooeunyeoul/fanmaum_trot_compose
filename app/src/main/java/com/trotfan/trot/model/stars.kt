package com.trotfan.trot.model
import kotlinx.serialization.Serializable

@Serializable
data class stars<T>(
    val meta: Meta,
    val stars: List<T>
)