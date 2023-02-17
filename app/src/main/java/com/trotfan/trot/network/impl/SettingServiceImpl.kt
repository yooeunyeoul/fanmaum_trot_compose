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
import io.ktor.util.*
import org.json.JSONObject
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

    @OptIn(InternalAPI::class)
    override suspend fun setPushSetting(
        userToken: String,
        userId: Long,
        alarm: Alarm
    ): CommonResponse<Unit> {
        return httpClient.patch(HttpRoutes.USERS + "/${userId}/alarms") {
            contentType(ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer $userToken"
            )
            val json2 = JSONObject()
            json2.put("night_alarm", alarm.nightAlarm)
            json2.put("day_alarm", alarm.dayAlarm)
            json2.put("free_tickets_gone", alarm.freeTicketsGone)
            json2.put("new_votes", alarm.newVotes)
            json2.put("time_event", alarm.timeEvent)
            val json = JSONObject()
            json.put("alarm_settings", json2)
            body = (json.toString())
        }.body()
    }
}