package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val service: SignUpService
) {
    suspend fun requestSmsCertification(
        phoneNumber: String,
    ): CommonResponse<SmsAuth> =
        service.requestCertificationCode(phoneNumber)

    suspend fun updateUser(
        userid: Int,
        nickName: String? = null,
        starId: Int? = null,
        phoneNumber: String? = null,
        redeemCode: String? = null
    ): CommonResponse<Unit> =
        service.updateUser(userid, nickName, starId, phoneNumber, redeemCode)


}