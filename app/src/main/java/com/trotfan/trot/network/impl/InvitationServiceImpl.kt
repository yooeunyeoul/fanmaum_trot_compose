package com.trotfan.trot.network.impl

import com.trotfan.trot.model.InviteCode
import com.trotfan.trot.network.InvitationService
import io.ktor.client.*
import javax.inject.Inject

class InvitationServiceImpl @Inject constructor(private val httpClient: HttpClient) :
    InvitationService {
    override suspend fun postInviteCode(inviteCode: InviteCode) {
        TODO("Not yet implemented")
    }
}