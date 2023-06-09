package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class VoteRepository @Inject constructor(
    private val voteService: VoteService
) {
    suspend fun getVote(): CommonResponse<VoteInfo>? =
        voteService.vote()

    suspend fun getStarRank(starId: Int): CommonResponse<FavoriteStarInfo> =
        voteService.starRank(starId)

    suspend fun getVoteTickets(token: String, userId: Long): CommonResponse<Tickets> =
        voteService.voteTickets(token = token, userId = userId)
}