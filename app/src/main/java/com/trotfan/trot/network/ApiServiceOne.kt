package com.trotfan.trot.network

import com.trotfan.trot.model.ApiResult
import com.trotfan.trot.model.UserInfo
import io.ktor.client.statement.*

interface ApiServiceOne {
    suspend fun getOneInfo(userInfo: UserInfo): HttpResponse
}