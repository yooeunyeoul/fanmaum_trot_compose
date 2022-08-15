package com.trotfan.trot.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserResponse(
    val code: Int? = 0,
    val errors: Errors? = null,
    val message: String? = null
//    val `data`: SignUpUserInfo? = null
)