package com.trotfan.trot.repository

import com.trotfan.trot.network.ApiServiceOne
import com.trotfan.trot.network.ApiServiceOneImpl
import javax.inject.Inject

class SampleRepository @Inject constructor(
    private val service: ApiServiceOne
) {

}