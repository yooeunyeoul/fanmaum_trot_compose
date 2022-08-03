package com.trotfan.trot.network.impl

import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.network.ApiServiceOne
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class ApiServiceOneImpl @Inject constructor(private val httpClient: HttpClient) : ApiServiceOne {
    override suspend fun getOneInfo(userInfo: UserInfo): HttpResponse =
        httpClient.get("/images/search?limit=5")


}