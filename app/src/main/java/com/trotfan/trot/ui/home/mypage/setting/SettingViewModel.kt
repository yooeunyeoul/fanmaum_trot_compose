package com.trotfan.trot.ui.home.mypage.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.trotfan.trot.datastore.*
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.MyPageRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AlarmType {
    day_alarm, night_alarm, free_tickets_gone, new_votes, time_event
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    application: Application
) : BaseViewModel(application) {

    lateinit var userInfoManager: UserInfoManager
    lateinit var userTicketManager: UserTicketManager
    private val context = getApplication<Application>()

    private val _dayEvent =
        MutableStateFlow(false)
    val dayEvent = _dayEvent.asStateFlow()

    private val _nightEvent =
        MutableStateFlow(false)
    val nightEvent = _nightEvent.asStateFlow()

    private val _freeVotes =
        MutableStateFlow(false)
    val freeVotes = _freeVotes.asStateFlow()

    private val _newVotes =
        MutableStateFlow(false)
    val newVotes = _newVotes.asStateFlow()

    private val _timeEvent =
        MutableStateFlow(false)
    val timeEvent = _timeEvent.asStateFlow()

    init {
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
        }
        getPushSetting()
    }

    private fun getPushSetting() {
        viewModelScope.launch {
            kotlin.runCatching {
                val userToken = context.userTokenStore.data.map {
                    it.token
                }.first()
                val userId = context.userIdStore.data.map {
                    it.userId
                }.first()

                Log.d("UserToken", userToken.toString())
                Log.d("UserId", userId.toString())

                myPageRepository.getPushSetting(
                    userId = userId,
                    userToken = userToken
                )
            }.onSuccess {
                when (it.result.code) {
                    ResultCodeStatus.SuccessWithData.code -> {
                        it.data?.run {
                            _dayEvent.emit(dayAlarm)
                            _nightEvent.emit(nightAlarm)
                            _freeVotes.emit(freeTicketsGone)
                            _newVotes.emit(newVotes)
                            _timeEvent.emit(timeEvent)
                        }
                    }
                }
            }.onFailure {
                Log.d("SettingViewModel", it.message.toString())
            }


        }
    }

    fun setPushSetting(alarmType: AlarmType, checked: Boolean) {
        viewModelScope.launch {
            kotlin.runCatching {
                val userToken = context.userTokenStore.data.map {
                    it.token
                }.first()
                val userId = context.userIdStore.data.map {
                    it.userId
                }.first()
                myPageRepository.setPushSetting(
                    userToken = userToken,
                    userId = userId,
                    alarmType = alarmType
                )
            }.onSuccess {
                when (it.result.code) {
                    ResultCodeStatus.SuccessWithNoData.code -> {
                        Log.d("SettingViewModel", "Success")
                        when (alarmType) {
                            AlarmType.day_alarm -> {
                                _dayEvent.emit(!_dayEvent.value)
                            }
                            AlarmType.night_alarm -> {
                                _nightEvent.emit(!_nightEvent.value)
                            }
                            AlarmType.new_votes -> {
                                _newVotes.emit(!_newVotes.value)
                            }
                            AlarmType.free_tickets_gone -> {
                                _freeVotes.emit(!_freeVotes.value)
                            }
                            AlarmType.time_event -> {
                                _timeEvent.emit(!_timeEvent.value)
                            }

                        }
                        if (checked) {
                            FirebaseMessaging.getInstance().subscribeToTopic(alarmType.name)
                        } else {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(alarmType.name)
                        }
                    }
                    else -> {
                        Log.d("SettingViewModel", it.toString())
                    }
                }

            }.onFailure {
                Log.d("SettingViewModel", it.message.toString())
            }
        }
    }

    fun dayEventPush() {

    }

    fun nightEventPush() {

    }

    fun freeVotesPush() {

    }

    fun newVotesPush() {

    }

    fun timeEventPush() {

    }

}