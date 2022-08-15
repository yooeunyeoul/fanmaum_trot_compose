package com.trotfan.trot.network

interface InvitationService {
    suspend fun postInviteCode(
        inviteCode: String?
    )
}