package com.trotfan.trot.network

import com.trotfan.trot.model.ServerState

interface ServerStateService {
    suspend fun getServerState(): ServerState
}