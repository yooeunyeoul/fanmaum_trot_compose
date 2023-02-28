package com.trotfan.trot.repository

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.model.VoteTicket
import com.trotfan.trot.network.ChargeService
import com.trotfan.trot.network.HomeService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val chargeService: ChargeService
) {
    suspend fun getMainPopups(token: String): CommonResponse<MainPopups> {
        return homeService.getMainPopups(token)
    }

    suspend fun postVoteTicket(voteTicket: VoteTicket, token: String): CommonResponse<Unit> {
        return homeService.postVoteTicket(voteTicket, token)
    }

    suspend fun postShareStar(userToken: String): CommonResponse<Unit> =
        chargeService.postShareStar(userToken = userToken)
}