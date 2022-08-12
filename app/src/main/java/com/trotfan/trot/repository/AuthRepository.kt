package com.trotfan.trot.repository

import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.ServerState
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.ServerStateService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val serverStateService: ServerStateService
) {
    suspend fun postKakaoLogin(kakaoTokens: KakaoTokens): UserInfo {
        return authService.postKakaoLogin(kakaoTokens)
    }

    suspend fun postGoogleLogin(authCode: GoogleToken): UserInfo {
        return authService.postGoogleLogin(authCode)
    }

    suspend fun getServerStateService(): ServerState {
        return serverStateService.getServerState()
    }
}