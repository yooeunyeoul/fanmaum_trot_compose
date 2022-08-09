package com.trotfan.trot.network.impl

import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(private val httpClient: HttpClient) : AuthService {
    override suspend fun postKakaoLogin(
        kakaoTokens: KakaoTokens
    ): UserInfo =
        httpClient.post {
            url(HttpRoutes.KAKAO_LOGIN)
            contentType(ContentType.Application.Json)
            setBody(kakaoTokens)
        }.body()

    override suspend fun postGoogleLogin(
        authCode: GoogleToken
    ): UserInfo =
        httpClient.post {
            url(HttpRoutes.GOOGLE_LOGIN)
            contentType(ContentType.Application.Json)
            setBody(authCode)
        }.body()
}