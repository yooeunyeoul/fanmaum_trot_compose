package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class MissionState(
    @SerializedName("rewarded")
    val rewarded: Boolean = false,
    @SerializedName("missions")
    val missions: Missions,
    @SerializedName("remaining")
    val remaining: Remaining
)

@kotlinx.serialization.Serializable
data class Missions(
    @SerializedName("attendance")
    val attendance: Boolean = false,
    @SerializedName("star_share")
    val star_share: Boolean = false,
    @SerializedName("video")
    val video: Boolean = false,
    @SerializedName("roulette")
    val roulette: Boolean = false
)

@kotlinx.serialization.Serializable
data class Remaining(
    @SerializedName("attendance")
    val attendance: Int = 0,
    @SerializedName("star_share")
    val star_share: Int = 0,
    @SerializedName("video")
    val video: Int = 0,
    @SerializedName("roulette")
    val roulette: Int = 0
)
