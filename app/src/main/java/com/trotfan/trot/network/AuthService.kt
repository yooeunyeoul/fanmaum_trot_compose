package com.trotfan.trot.network

import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import io.ktor.client.statement.*
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.security.auth.callback.Callback

interface AuthService {
    suspend fun postKakaoLogin(
        kakaoTokens: KakaoTokens
    ): UserInfo

    suspend fun postGoogleLogin(
        authCode: String
    ): UserInfo
}