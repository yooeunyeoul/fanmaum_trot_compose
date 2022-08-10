package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
class SmsCertificationRequestResult(
    val result_code: String,
    val cmid: String,
    val resultMessage: String
) {
}