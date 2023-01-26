package com.trotfan.trot.repository

import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postLogout(token: String): CommonResponse<Unit> {
        return authService.postLogout(token)
    }
}