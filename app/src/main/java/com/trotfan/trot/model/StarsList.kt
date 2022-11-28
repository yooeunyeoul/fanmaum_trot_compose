package com.trotfan.trot.model
import kotlinx.serialization.Serializable

@Serializable
data class StarsList<T>(
    val meta: Meta,
    val stars: List<T>
)