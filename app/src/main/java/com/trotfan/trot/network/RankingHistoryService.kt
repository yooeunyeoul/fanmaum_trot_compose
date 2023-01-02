package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse

interface RankingHistoryService {
    suspend fun getMonthlyStarList(
        year: String?,
        month: String?
    ): CommonResponse<StarRankingList>

    suspend fun getDailyStarList(
        year: String,
        month: String,
        day: String
    ): CommonResponse<StarRankingDailyList>

    suspend fun getStarRankingDetail(
        starId: Int,
        year: String,
        month: String
    ): CommonResponse<List<StarRankingDetail>>

    suspend fun getDatePickerRange(): CommonResponse<DatePickerRange>

    suspend fun getBanners(
        group: String, platform: String
    ): CommonListResponse<Banner>
}