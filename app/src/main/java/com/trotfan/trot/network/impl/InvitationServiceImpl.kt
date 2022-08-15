package com.trotfan.trot.network.impl

import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.InvitationService
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class InvitationServiceImpl @Inject constructor(private val httpClient: HttpClient) :
    InvitationService {
    override suspend fun postInviteCode(inviteCode: String?) {
        httpClient.post {
            url("${HttpRoutes.USER}/496b3074/recommend")
        }
        httpClient.patch {
            url("${HttpRoutes.USER}/496b3074/recommend")
        }
    }
}