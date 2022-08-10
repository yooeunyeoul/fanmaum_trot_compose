package com.trotfan.trot.network.impl

import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.InviteCode
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.InvitationService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class InvitationServiceImpl @Inject constructor(private val httpClient: HttpClient) :
    InvitationService {
    override suspend fun postInviteCode(inviteCode: InviteCode) {
        TODO("Not yet implemented")
    }
}