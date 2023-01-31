package com.trotfan.trot.network

import com.trotfan.trot.model.*
import com.trotfan.trot.network.response.CommonResponse
import java.io.File

interface UserService {
    suspend fun updateUser(
        userId: Long,
        nickName: String?,
        starId: Int?,
        phoneNumber: String?,
        redeemCode: String?,
        agreesTerms: Boolean?,
        token: String
    ): CommonResponse<Unit>

    suspend fun userProfileUpload(
        token: String,
        userId: Long,
        image: File
    ): CommonResponse<ProfileImage>

    suspend fun userTicketHistory(
        cursor: String,
        token: String,
        userId: Long,
        filter: String?
    ): CommonResponse<TicketsHistory>
}