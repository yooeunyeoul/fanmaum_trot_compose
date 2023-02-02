package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.home.mypage.setting.AlarmType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val repository: SignUpRepository,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()
    private var userId: Long? = null
    private var userUpdateResult by mutableStateOf(false)
    private var dayPushUpdateResult by mutableStateOf(false)
    private var nightPushUpdateResult by mutableStateOf(false)
    var apiResultState = MutableStateFlow(
        userUpdateResult && dayPushUpdateResult && nightPushUpdateResult
    )

    init {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                userId = it.userId
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.updateUser(
                    userid = userId ?: 0,
                    agrees_terms = true,
                    token = userLocalToken.value?.token ?: ""
                )
            }.onSuccess {
                userUpdateResult = true
                if (userUpdateResult && dayPushUpdateResult && nightPushUpdateResult) {
                    apiResultState.emit(true)
                }
            }.onFailure {

            }
        }
    }

    fun patchPushSettingNight() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.patchPushSetting(
                    token = userLocalToken.value?.token ?: "",
                    id = userId ?: 0,
                    type = AlarmType.night_alarm
                )
            }.onSuccess {
                nightPushUpdateResult = true
                if (userUpdateResult && dayPushUpdateResult && nightPushUpdateResult) {
                    apiResultState.emit(true)
                }
            }
        }
    }

    fun patchPushSettingDay() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.patchPushSetting(
                    token = userLocalToken.value?.token ?: "",
                    id = userId ?: 0,
                    type = AlarmType.day_alarm
                )
            }.onSuccess {
                dayPushUpdateResult = true
                if (userUpdateResult && dayPushUpdateResult && nightPushUpdateResult) {
                    apiResultState.emit(true)
                }
            }
        }
    }
}
