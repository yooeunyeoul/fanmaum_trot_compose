package com.trotfan.trot.repository

import com.trotfan.trot.network.InvitationService
import javax.inject.Inject

class InvitationRepository @Inject constructor(
    private val service: InvitationService
) {
    suspend fun postInviteCode(inviteCode: String?) {
        return service.postInviteCode(inviteCode)
    }
}