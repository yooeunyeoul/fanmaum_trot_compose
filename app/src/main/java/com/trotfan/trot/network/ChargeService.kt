package com.trotfan.trot.network

import com.trotfan.trot.model.*
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


    suspend fun getMissions(
        userToken: String
    ): CommonResponse<MissionState>

    suspend fun postRewardVideo(
        userToken: String
    ): CommonResponse<RewardTickets>

    suspend fun postAttendance(
        userToken: String
    ): CommonResponse<Ticket>

    suspend fun postShareStar(
        userToken: String
    ): CommonResponse<Unit>
}