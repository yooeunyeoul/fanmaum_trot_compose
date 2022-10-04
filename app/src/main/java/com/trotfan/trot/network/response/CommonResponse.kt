package com.trotfan.trot.network.response
import com.trotfan.trot.model.Result
import kotlinx.serialization.Serializable

@Serializable
data class CommonResponse<T>(
    val `data`: T,
    val result: Result
)