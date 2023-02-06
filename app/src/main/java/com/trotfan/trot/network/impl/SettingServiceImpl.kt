package com.trotfan.trot.network.impl

import com.trotfan.trot.model.Alarm
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.SettingService
import com.trotfan.trot.network.response.CommonResponse
import com.trotfan.trot.ui.home.mypage.setting.AlarmType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class SettingServiceImpl @Inject constructor(private val httpClient: HttpClient) : SettingService {
    override suspend fun getPushSetting(userToken: String, userId: Long): CommonResponse<Alarm> {
        val response = httpClient.get(HttpRoutes.USERS + "/${userId}/alarms") {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $userToken"

            )
        }
        return response.body()
    }

    override suspend fun setPushSetting(
        userToken: String,
        userId: Long,
        alarmType: AlarmType
    ): CommonResponse<Unit> {
        return httpClient.patch(HttpRoutes.USERS + "/${userId}/alarms/${alarmType.name}") {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $userToken"
            )
        }.body()
    }
}