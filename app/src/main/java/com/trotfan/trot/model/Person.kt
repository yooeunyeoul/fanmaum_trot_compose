package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Int,
    val image: String,
    val name: String,
    val group: String
)