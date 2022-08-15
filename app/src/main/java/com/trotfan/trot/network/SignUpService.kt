package com.trotfan.trot.network

import com.trotfan.trot.model.*

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String,
        message: String
    ): SmsCertificationRequestResult

    suspend fun getStarList(
        page: Int,
    ): List<Person>

    suspend fun starSearch(
        page: Int,
        name: String
    ): List<Person>

    suspend fun updateUser(
        userId: String,
        nickName: String?,
        starId: String?,
        phoneNumber: String?,
        redeemCode: String?
    ): UpdateUserResponse

}