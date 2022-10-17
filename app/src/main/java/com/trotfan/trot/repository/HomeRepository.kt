package com.trotfan.trot.repository

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.network.HomeService
import com.trotfan.trot.network.response.CommonResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getMainPopups(): CommonResponse<MainPopups> {
        return homeService.getMainPopups()
    }
}