package com.trotfan.trot.network

import com.trotfan.trot.model.*

interface AuthService {
    suspend fun postKakaoLogin(
        kakaoTokens: KakaoTokens
    ): UserToken

    suspend fun postGoogleLogin(
        authCode: GoogleToken
    ): UserToken

    suspend fun getUserInfo(
        userId: Int
    ): UserInfoData
}