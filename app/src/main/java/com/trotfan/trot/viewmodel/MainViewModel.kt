package com.trotfan.trot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.model.ApiResult
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {
    private val _testData = MutableStateFlow<List<ApiResult>>(emptyList())
    val testData: StateFlow<List<ApiResult>>
        get() = _testData

    init {
        getRestApiTest()
        Log.d("Initializing", "MainViewModel")
    }

    private fun getRestApiTest() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getApiServiceOne(
                userInfo = UserInfo(
                    id = "asdf",
                    pw = "1234",
                    nickname = "Seung Hee",
                    stateMessage = "Log Success"
                )
            )
        }

    }
}