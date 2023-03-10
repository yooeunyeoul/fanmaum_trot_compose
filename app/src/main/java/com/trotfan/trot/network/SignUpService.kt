package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonResponse
import com.trotfan.trot.ui.signup.viewmodel.FlavorStatus

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String,
        hashKey: String
    ): CommonResponse<SmsAuth>

    suspend fun getStarList(
        cursor: String,
        search: String
    ): CommonResponse<StarsList<FavoriteStar>>

    suspend fun updateUser(
        userId: Long,
        nickName: String?,
        starId: Int?,
        phoneNumber: String?,
        agrees_terms: Boolean?,
        token: String
    ): CommonResponse<Unit>

    suspend fun postUserRedeemCode(
        userId: Long,
        redeemCode: String?,
        token: String
    ): CommonResponse<Unit>

}