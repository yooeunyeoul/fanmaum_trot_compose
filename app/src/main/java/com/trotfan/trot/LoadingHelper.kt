package com.trotfan.trot

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadingHelper @Inject constructor(
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _progressbar =
        MutableStateFlow<Boolean>(false)
    val progressbar = _progressbar.asStateFlow()


    init {
        Log.e("이거 LoadingHelper", "LoadingHelper")
    }

    fun hideProgress() {
        coroutineScope.launch {
            _progressbar.emit(false)
        }

    }

    fun showProgress() {
        coroutineScope.launch {
            _progressbar.emit(true)
        }

    }


}