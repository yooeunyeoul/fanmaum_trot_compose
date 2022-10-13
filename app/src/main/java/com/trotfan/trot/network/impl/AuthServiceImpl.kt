package com.trotfan.trot.network.impl

import com.trotfan.trot.model.*
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : AuthService {
    override suspend fun postKakaoLogin(
        kakaoTokens: KakaoTokens
    ): CommonResponse<UserToken> =
        httpClient.post {
            url(HttpRoutes.KAKAO_LOGIN)
            contentType(ContentType.Application.Json)
            setBody(kakaoTokens)
        }.body()

    override suspend fun postGoogleLogin(
        authCode: GoogleToken
    ): CommonResponse<UserToken> {
        val responses = httpClient.post {
            url(HttpRoutes.GOOGLE_LOGIN)
            contentType(ContentType.Application.Json)
            setBody(authCode)
        }
        return responses.body()
    }

    override suspend fun postAppleLogin(authCode: AppleToken): CommonResponse<UserToken> {
        val response = httpClient.post {
            url(HttpRoutes.APPLE_LOGIN)
            contentType(ContentType.Application.Json)
            setBody(authCode)
        }
        return response.body()
    }

    override suspend fun getUserInfo(userId: Int): CommonResponse<UserInfo> =
        httpClient.get {
            url("${HttpRoutes.USERS}/$userId")
        }.body()
}