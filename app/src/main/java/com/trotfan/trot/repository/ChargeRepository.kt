package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.ChargeService
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class ChargeRepository @Inject constructor(
    private val voteService: VoteService,
    private val chargeService: ChargeService
) {

    suspend fun getVoteTickets(userId: Long): CommonResponse<Tickets> =
        voteService.voteTickets(userId)

    suspend fun certificationCharge(
        userId: Int,
        productId: String,
        purchaseToken: String,
        packageName: String
    ): CommonResponse<Unit> =
        chargeService.certificationCharge(userId, productId, purchaseToken, packageName)
}