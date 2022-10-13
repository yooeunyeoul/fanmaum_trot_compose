package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.ServerStateService
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class VoteRepository @Inject constructor(
    private val voteService: VoteService
) {
    suspend fun getVoteList(): CommonListResponse<Top3Benefit> =
        voteService.VoteList()

}