package com.trotfan.trot.network.impl

import android.content.Context
import com.trotfan.trot.model.ServerState
import com.trotfan.trot.network.HttpRoutes
import com.trotfan.trot.network.ServerStateService
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class ServerStateServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val httpClient: HttpClient
) : ServerStateService {
    override suspend fun getServerState(): ServerState {
        return httpClient.get {
            url(HttpRoutes.SERVER_STATE)
        }.body()
    }
}