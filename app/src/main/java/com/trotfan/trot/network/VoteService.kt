package com.trotfan.trot.network

import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.Tickets
import com.trotfan.trot.model.VoteInfo
import com.trotfan.trot.network.response.CommonResponse

interface VoteService {
    suspend fun vote(
    ): CommonResponse<VoteInfo>

    suspend fun starRank(
        starId: Int
    ): CommonResponse<FavoriteStarInfo>

    suspend fun voteTickets(
        userId: Long
    ): CommonResponse<Tickets>
}