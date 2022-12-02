package com.trotfan.trot.network

import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.Tickets
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.network.response.CommonResponse

interface VoteService {
    suspend fun vote(
    ): CommonResponse<Top3Benefit>

    suspend fun starRank(
        starId: Int
    ): CommonResponse<FavoriteStarInfo>

    suspend fun voteTickets(
        userId: Long
    ): CommonResponse<Tickets>
}