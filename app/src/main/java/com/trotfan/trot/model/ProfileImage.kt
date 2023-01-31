package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImage(
    @SerializedName("image")
    val image: String
)
