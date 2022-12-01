package com.trotfan.trot.network

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.model.VoteTicket
import com.trotfan.trot.network.response.CommonResponse

interface HomeService {
    suspend fun getMainPopups(token: String): CommonResponse<MainPopups>
    suspend fun postVoteTicket(voteTicket: VoteTicket, token: String): CommonResponse<Unit>
}