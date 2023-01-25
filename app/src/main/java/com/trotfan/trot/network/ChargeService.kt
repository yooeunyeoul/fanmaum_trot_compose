package com.trotfan.trot.network

import com.trotfan.trot.network.response.CommonResponse

interface ChargeService {
    suspend fun certificationCharge(
        userId: Int,
        productId: String,
        purchaseToken: String
    ): CommonResponse<Unit>

}