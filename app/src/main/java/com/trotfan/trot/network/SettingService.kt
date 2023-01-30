package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonResponse
import com.trotfan.trot.ui.home.mypage.setting.AlarmType

interface SettingService {
    suspend fun getPushSetting(
        userToken: String,
        userId: Long
    ): CommonResponse<Alarm>

    suspend fun setPushSetting(
        userToken: String,
        userId: Long,
        alarmType: AlarmType
    ): CommonResponse<Unit>
}