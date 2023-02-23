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

    suspend fun getVoteTickets(userId: Long, userToken: String): CommonResponse<Tickets> =
        voteService.voteTickets(userId, userToken)

    suspend fun certificationCharge(
        userToken: String,
        userId: Int,
        productId: String,
        purchaseToken: String,
        packageName: String
    ): CommonResponse<Unit> =
        chargeService.certificationCharge(userToken, userId, productId, purchaseToken, packageName)

    suspend fun checkRoulette(
        userToken: String,
        userId: Int,
    ): CommonResponse<LuckyTicket> = chargeService.checkRoulette(userToken, userId)

    suspend fun rewardRoulette(
        userToken: String,
        userId: Int,
    ): CommonResponse<LuckyTicket> = chargeService.rewardRoulette(userToken, userId)

    suspend fun getMissions(userToken: String): CommonResponse<MissionState> =
        chargeService.getMissions(userToken)

    suspend fun postRewardVideo(userToken: String): CommonResponse<RewardTickets> =
        chargeService.postRewardVideo(userToken = userToken)

    suspend fun postAttendance(userToken: String): CommonResponse<Tickets> =
        chargeService.postAttendance(userToken = userToken)

    suspend fun postShareStar(userToken: String): CommonResponse<Unit> =
        chargeService.postShareStar(userToken = userToken)

    suspend fun postMission(userToken: String): CommonResponse<Tickets> =
        chargeService.postMission(userToken = userToken)
}