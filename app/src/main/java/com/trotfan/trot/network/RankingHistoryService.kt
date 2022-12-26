package com.trotfan.trot.network

import com.trotfan.trot.model.DatePickerRange
import com.trotfan.trot.model.Banner
import com.trotfan.trot.model.StarRankingDetail
import com.trotfan.trot.model.StarRankingList
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse

interface RankingHistoryService {
    suspend fun getMonthlyStarList(
        year: String,
        month: String
    ): CommonResponse<StarRankingList>

    suspend fun getDailyStarList(
        year: String,
        month: String,
        day: String
    ): CommonResponse<StarRankingList>

    suspend fun getStarRankingDetail(
        starId: Int
    ): CommonResponse<List<StarRankingDetail>>

    suspend fun getDatePickerRange(): CommonResponse<DatePickerRange>

    suspend fun getBanners(
        group: String, platform: String
    ): CommonListResponse<Banner>
}