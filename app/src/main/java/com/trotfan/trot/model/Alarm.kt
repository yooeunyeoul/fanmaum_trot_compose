package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alarm(
    @SerialName("day_alarm")
    val dayAlarm: Boolean,
    @SerialName("free_tickets_gone")
    val freeTicketsGone: Boolean,
    @SerialName("new_votes")
    val newVotes: Boolean,
    @SerialName("night_alarm")
    val nightAlarm: Boolean,
    @SerialName("time_event")
    val timeEvent: Boolean
)