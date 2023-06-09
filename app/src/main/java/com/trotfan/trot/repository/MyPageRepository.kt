package com.trotfan.trot.repository

import com.trotfan.trot.model.Alarm
import com.trotfan.trot.model.InviteInfo
import com.trotfan.trot.model.ProfileImage
import com.trotfan.trot.model.TicketsHistory
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.UserService
import com.trotfan.trot.network.SettingService
import com.trotfan.trot.network.response.CommonResponse
import java.io.File
import com.trotfan.trot.ui.home.mypage.setting.AlarmType
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val settingService: SettingService
) {
    suspend fun postLogout(token: String): CommonResponse<Unit> {
        return authService.postLogout(token)
    }

    suspend fun postUserProfile(
        token: String,
        userId: Long,
        file: File
    ): CommonResponse<ProfileImage> {
        return userService.userProfileUpload(token = token, userId = userId, image = file)
    }

    suspend fun getPushSetting(userToken: String, userId: Long): CommonResponse<Alarm> {
        return settingService.getPushSetting(userToken, userId)
    }

    suspend fun setPushSetting(
        userToken: String,
        userId: Long,
        alarm: Alarm
    ): CommonResponse<Unit> {
        return settingService.setPushSetting(userToken, userId, alarm)
    }

    suspend fun signOut(
        userToken: String,
        userId: Long,
        reason: Int,
        etc: String?
    ): CommonResponse<Unit> {
        return userService.signOut(userToken, userId, reason, etc)
    }

    suspend fun getUserInviteInfo(
        userToken: String,
        userId: Long
    ): CommonResponse<InviteInfo> {
        return userService.getUserInviteInfo(token = userToken, userId = userId)
    }
}