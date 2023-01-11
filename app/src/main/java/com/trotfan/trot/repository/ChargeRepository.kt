package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class ChargeRepository @Inject constructor(
    private val voteService: VoteService
) {

    suspend fun getVoteTickets(userId: Long): CommonResponse<Tickets> =
        voteService.voteTickets(userId)
}