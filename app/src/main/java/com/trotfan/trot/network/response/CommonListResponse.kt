package com.trotfan.trot.network.response
import com.trotfan.trot.model.Result
import kotlinx.serialization.Serializable

@Serializable
data class CommonListResponse<T>(
    val `data`: List<T>,
    val result: Result
)