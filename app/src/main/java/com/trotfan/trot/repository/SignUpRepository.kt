package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.SettingService
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.response.CommonResponse
import com.trotfan.trot.ui.signup.viewmodel.FlavorStatus
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val service: SignUpService,
    private val settingService: SettingService
) {
    suspend fun requestSmsCertification(
        phoneNumber: String,
        hashKey: String
    ): CommonResponse<SmsAuth> =
        service.requestCertificationCode(phoneNumber, hashKey)

    suspend fun updateUser(
        userid: Long,
        nickName: String? = null,
        starId: Int? = null,
        phoneNumber: String? = null,
        agrees_terms: Boolean? = null,
        token: String
    ): CommonResponse<Unit> =
        service.updateUser(userid, nickName, starId, phoneNumber, agrees_terms, token)


    suspend fun patchPushSetting(
        token: String,
        id: Long,
        alarm: Alarm
    ): CommonResponse<Unit> =
        settingService.setPushSetting(userToken = token, userId = id, alarm = alarm)

    suspend fun postUserRedeemCode(
        userid: Long,
        redeemCode: String? = null,
        token: String
    ): CommonResponse<Unit> =
        service.postUserRedeemCode(userid, redeemCode, token)
}