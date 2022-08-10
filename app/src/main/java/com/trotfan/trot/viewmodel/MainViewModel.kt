package com.trotfan.trot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.trotfan.trot.model.ApiResult
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    private val _testData = MutableStateFlow<List<ApiResult>>(emptyList())
    val testData: StateFlow<List<ApiResult>>
        get() = _testData

    init {

        Log.d("Initializing", "MainViewModel")
    }

}