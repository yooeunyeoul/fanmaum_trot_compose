package com.trotfan.trot.repository

import com.trotfan.trot.model.SmsCertificationRequestResult
import com.trotfan.trot.model.UpdateUserResponse
import com.trotfan.trot.network.SignUpService
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val service: SignUpService
) {
    suspend fun requestSmsCertification(
        phoneNumber: String,
        message: String
    ): SmsCertificationRequestResult =
        service.requestCertificationCode(phoneNumber, message)

    suspend fun updateUser(
        userid: String,
        nickName: String? = null,
        starId: String? = null,
        phoneNumber: String? = null,
        redeemCode: String? = null
    ): UpdateUserResponse =
        service.updateUser(userid, nickName, starId, phoneNumber, redeemCode)


}