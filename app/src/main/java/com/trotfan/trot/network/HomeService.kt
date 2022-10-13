package com.trotfan.trot.network

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.network.response.CommonResponse

interface HomeService {
    suspend fun getMainPopups(): CommonResponse<MainPopups>
}