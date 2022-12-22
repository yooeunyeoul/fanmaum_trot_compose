package com.trotfan.trot.network

import com.trotfan.trot.model.MonthStarRankInfo
import com.trotfan.trot.network.response.CommonResponse

interface RankService {

    suspend fun monthStarRank(
        month: Int
    ): CommonResponse<MonthStarRankInfo>
}