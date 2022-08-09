package com.trotfan.trot.network

import com.trotfan.trot.model.InviteCode

interface InvitationService {
    suspend fun postInviteCode(
        inviteCode: InviteCode
    )
}