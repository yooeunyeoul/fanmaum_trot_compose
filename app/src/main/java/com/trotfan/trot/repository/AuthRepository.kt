package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.ServerStateService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val serverStateService: ServerStateService
) {
    suspend fun postKakaoLogin(kakaoTokens: KakaoTokens): UserToken {
        return authService.postKakaoLogin(kakaoTokens)
    }

    suspend fun postGoogleLogin(authCode: GoogleToken): UserToken {
        return authService.postGoogleLogin(authCode)
    }

    suspend fun getServerStateService(): ServerState {
        return serverStateService.getServerState()
    }

    suspend fun getUserInfo(userId: Int): UserInfoData {
        return authService.getUserInfo(userId)
    }
}