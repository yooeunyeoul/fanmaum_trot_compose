package com.trotfan.trot.network.impl

import coil.request.SuccessResult
import com.trotfan.trot.model.*
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.response.CommonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import javax.inject.Inject


class VoteServiceImpl @Inject constructor(private val httpClient: HttpClient) : VoteService {

}
