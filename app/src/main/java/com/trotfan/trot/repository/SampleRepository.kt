package com.trotfan.trot.repository

import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.ApiServiceOne
import javax.inject.Inject

class SampleRepository @Inject constructor(
    private val service: ApiServiceOne
) {
    suspend fun getApiServiceOne(userInfo: UserInfo) {
        service.getOneInfo(userInfo)
    }
}