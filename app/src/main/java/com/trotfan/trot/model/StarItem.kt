package com.trotfan.trot.model
import kotlinx.serialization.Serializable

@Serializable
data class StarItem(
    val `data`: List<Person>
)