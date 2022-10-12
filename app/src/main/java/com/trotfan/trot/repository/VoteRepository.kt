package com.trotfan.trot.repository

import com.trotfan.trot.model.*
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.ServerStateService
import com.trotfan.trot.network.VoteService
import javax.inject.Inject

class VoteRepository @Inject constructor(
    private val voteService: VoteService
) {

}