package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class InviteInfo(
    @SerializedName("code")
    val code: String,
    @SerializedName("invited")
    val invited: Int,
    @SerializedName("tickets")
    val tickets: Long
)
