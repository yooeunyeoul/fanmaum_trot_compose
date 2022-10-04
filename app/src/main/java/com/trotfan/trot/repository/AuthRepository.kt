package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.ServerStateService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val serverStateService: ServerStateService
) {
    suspend fun postKakaoLogin(kakaoTokens: KakaoTokens): CommonResponse<UserToken> {
        return authService.postKakaoLogin(kakaoTokens)
    }

    suspend fun postGoogleLogin(authCode: GoogleToken): CommonResponse<UserToken> {
        return authService.postGoogleLogin(authCode)
    }

    suspend fun postAppleLogin(authCode: AppleToken): CommonResponse<UserToken> {
        return authService.postAppleLogin(authCode)
    }

    suspend fun getServerStateService(): ServerState {
        return serverStateService.getServerState()
    }

    suspend fun getUserInfo(userId: Int): CommonResponse<UserInfo> {
        return authService.getUserInfo(userId)
    }
}