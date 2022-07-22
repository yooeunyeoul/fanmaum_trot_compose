package com.trotfan.trot.network

import com.trotfan.trot.model.ApiResult
import com.trotfan.trot.model.UserInfo

interface ApiServiceOne {
    suspend fun getOneInfo(userInfo: UserInfo) : List<ApiResult>
}