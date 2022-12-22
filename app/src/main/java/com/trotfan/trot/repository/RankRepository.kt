package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.RankService
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class RankRepository @Inject constructor(
    private val rankService: RankService
) {
    suspend fun getMonthStarRank(month: Int): CommonResponse<MonthStarRankInfo> =
        rankService.monthStarRank(month = month)
}