package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.Alarm
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
    var apiUserUpdateResultState = MutableStateFlow(
        false
    )
    var apiPushResultState = MutableStateFlow(
        false
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
                apiUserUpdateResultState.emit(true)
            }.onFailure {

            }
        }
    }

    fun patchPushSetting(night: Boolean, day: Boolean) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.patchPushSetting(
                    token = userLocalToken.value?.token ?: "",
                    id = userId ?: 0,
                    alarm = Alarm(
                        dayAlarm = day,
                        timeEvent = day,
                        nightAlarm = night,
                        freeTicketsGone = night,
                        newVotes = night
                    )
                )
            }.onSuccess {
                apiPushResultState.emit(true)
            }
        }
    }
}
