package com.trotfan.trot.viewmodel

import androidx.lifecycle.ViewModel
import com.trotfan.trot.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {

}