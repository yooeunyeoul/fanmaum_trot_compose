package com.trotfan.trot.network.impl

import com.trotfan.trot.network.ApiServiceTwo
import io.ktor.client.*
import javax.inject.Inject

class ApiServiceTwoImpl @Inject constructor(httpClient: HttpClient) : ApiServiceTwo {
    override suspend fun getTwoInfo() {

    }
}