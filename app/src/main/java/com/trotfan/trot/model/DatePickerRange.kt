package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class DatePickerRange(
    val started_at: String,
    val ended_at: String
)
