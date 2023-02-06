package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MainPopups(
    @SerializedName("update")
    val update: Update?,
    @SerializedName("layers")
    val layers: List<Layer>?,
    @SerializedName("is_rewarded")
    val is_rewarded: Boolean,
    @SerializedName("auto_vote")
    val auto_vote: AutoVote
)

@Serializable
data class Update(
    @SerializedName("version")
    val version: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)

@Serializable
data class Layer(
    @SerializedName("order")
    val order: Int,
    @SerializedName("image")
    val image: String
)

@Serializable
data class AutoVote(
    @SerializedName("is_voted")
    val is_voted: Boolean,
    @SerializedName("star")
    val star: StarName,
    @SerializedName("quantity")
    val quantity: Int
)

@Serializable
data class StarName(
    @SerializedName("name")
    val name: String
)
