package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.RankingHistoryService
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class RankingHistoryRepository @Inject constructor(
    private val rankingHistoryService: RankingHistoryService
) {
    suspend fun getMonthlyStarList(
        year: String,
        month: String
    ): CommonResponse<StarRankingList> = rankingHistoryService.getMonthlyStarList(year, month)

    suspend fun getDailyStarList(
        year: String,
        month: String,
        day: String
    ): CommonResponse<StarRankingDailyList> =
        rankingHistoryService.getDailyStarList(year, month, day)

    suspend fun getStarRankingDetail(
        starId: Int,
        year: String,
        month: String
    ): CommonResponse<List<StarRankingDetail>> =
        rankingHistoryService.getStarRankingDetail(starId, year, month)

    suspend fun getDatePickerRange(
    ): CommonResponse<DatePickerRange> = rankingHistoryService.getDatePickerRange()

    suspend fun getBanners(
        group: String, platform: String
    ): CommonListResponse<Banner> = rankingHistoryService.getBanners(group, platform)
}