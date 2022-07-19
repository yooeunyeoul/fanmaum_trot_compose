package com.trotfan.trot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.trotfan.trot.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {
    init {
        Log.d("야 이게 나온다고 ㅋㅋㅋ","아아아아아아")
    }
}