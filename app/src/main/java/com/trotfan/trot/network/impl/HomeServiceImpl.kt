package com.trotfan.trot.network.impl

import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.network.HomeService
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class HomeServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): HomeService {
    override suspend fun getMainPopups(): CommonResponse<MainPopups> {
        return httpClient.get {
            url(HttpRoutes.POPUPS)
        }.body()
    }
}