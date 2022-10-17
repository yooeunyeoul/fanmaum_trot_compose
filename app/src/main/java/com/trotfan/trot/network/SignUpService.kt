package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonListResponse
import com.trotfan.trot.network.response.CommonResponse

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String
    ): CommonResponse<SmsAuth>

    suspend fun getStarList(
        cursor: String,
        search: String
    ): CommonResponse<stars<FavoriteStar>>

    suspend fun starSearch(
        cursor: String,
        name: String
    ): CommonResponse<stars<FavoriteStar>>

    suspend fun updateUser(
        userId: Int,
        nickName: String?,
        starId: Int?,
        phoneNumber: String?,
        redeemCode: String?
    ): CommonResponse<Unit>

}