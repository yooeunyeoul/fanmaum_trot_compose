package com.trotfan.trot.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class SmsCertificationRequestResult(
    val result_code: String? = null,
    val cmid: String? = null,
    val resultMessage: String? = null
) {
}