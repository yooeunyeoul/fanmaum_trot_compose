package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonResponse

interface AuthService {
    suspend fun postKakaoLogin(
        kakaoTokens: KakaoTokens
    ): CommonResponse<UserToken>

    suspend fun postGoogleLogin(
        authCode: GoogleToken
    ): CommonResponse<UserToken>

    suspend fun postAppleLogin(
        authCode: AppleToken
    ): CommonResponse<UserToken>

    suspend fun getUserInfo(
        userId: Int
    ): CommonResponse<UserInfo>
}