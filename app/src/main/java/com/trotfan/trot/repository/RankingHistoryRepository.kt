package com.trotfan.trot.repository

import com.trotfan.trot.model.Banner
import com.trotfan.trot.model.StarRankingDetail
import com.trotfan.trot.model.StarRankingList
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
    ): CommonResponse<StarRankingList> = rankingHistoryService.getDailyStarList(year, month, day)

    suspend fun getStarRankingDetail(
        starId: Int
    ): CommonResponse<List<StarRankingDetail>> = rankingHistoryService.getStarRankingDetail(starId)

    suspend fun getBanners(
        group: String, platform: String
    ): CommonListResponse<Banner> = rankingHistoryService.getBanners(group, platform)
}