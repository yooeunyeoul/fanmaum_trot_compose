package com.trotfan.trot.network

import com.trotfan.trot.model.LuckyTicket
import com.trotfan.trot.network.response.CommonResponse

interface ChargeService {
    suspend fun certificationCharge(
        userToken: String,
        userId: Int,
        productId: String,
        purchaseToken: String,
        packageName: String
    ): CommonResponse<Unit>

    suspend fun checkRoulette(
        userToken: String,
        userId: Int,
    ): CommonResponse<LuckyTicket>


}