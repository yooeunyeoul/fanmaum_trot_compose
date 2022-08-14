package com.trotfan.trot.network

import com.trotfan.trot.model.Person
import com.trotfan.trot.model.SmsCertificationRequestResult

interface SignUpService {
    suspend fun requestCertificationCode(
        phoneNumber: String,
        message: String
    ): SmsCertificationRequestResult

    suspend fun getStarList(
        page: Int,
    ): List<Person>

    suspend fun starSearch(
        page: Int,
        name: String
    ): List<Person>
}