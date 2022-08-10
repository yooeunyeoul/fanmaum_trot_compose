package com.trotfan.trot.network

import com.trotfan.trot.model.SmsCertificationRequestResult

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String,
        message: String
    ): SmsCertificationRequestResult
}