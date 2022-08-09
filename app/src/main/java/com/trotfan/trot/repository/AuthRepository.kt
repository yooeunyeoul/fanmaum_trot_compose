package com.trotfan.trot.repository

import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: AuthService
) {
    suspend fun postKakaoLogin(kakaoTokens: KakaoTokens): UserInfo {
        return service.postKakaoLogin(kakaoTokens)
    }

    suspend fun postGoogleLogin(authCode: GoogleToken): UserInfo {
        return service.postGoogleLogin(authCode)
    }
}