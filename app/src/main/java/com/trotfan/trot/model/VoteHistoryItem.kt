package com.trotfan.trot.model

data class VoteHistoryItem(
    val date: String,
    val state: Int,
    val title: String,
    val cnt: Int
)
