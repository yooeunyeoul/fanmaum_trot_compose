package com.trotfan.trot.repository

import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.InviteCode
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.InvitationService
import javax.inject.Inject

class InvitationRepository @Inject constructor(
    private val service: InvitationService
) {
    suspend fun postInviteCode(inviteCode: InviteCode) {
        return service.postInviteCode(inviteCode)
    }
}