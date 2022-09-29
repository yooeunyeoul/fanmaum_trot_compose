package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String
    ): ReturnStatus

    suspend fun getStarList(
        cursor: String,
    ): CommonResponse<stars<Star>>

    suspend fun starSearch(
        cursor: String,
        name: String
    ): CommonResponse<stars<Star>>

    suspend fun updateUser(
        userId: String,
        nickName: String?,
        starId: String?,
        phoneNumber: String?,
        redeemCode: String?
    ): UpdateUserResponse

}