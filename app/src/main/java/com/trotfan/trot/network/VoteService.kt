package com.trotfan.trot.network

import com.trotfan.trot.model.SmsAuth
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse

interface VoteService {
    suspend fun vote(
    ): CommonResponse<Top3Benefit>
}