package com.trotfan.trot.ui.home.mypage.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.di.ApiResult
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.MyPageRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

enum class AlarmType {
    day_alarm, night_alarm, free_tickets_gone, new_votes, time_event
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val loadingHelper: LoadingHelper,
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

    private val _secessionConfirmDialogState =
        MutableStateFlow(false)
    val secessionConfirmDialogState = _secessionConfirmDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
        }
        getPushSetting()
    }

    private fun getPushSetting() {
        viewModelScope.launch {
            loadingHelper.showProgress()
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
                apiStatus.value = ApiResult.Success("Success")
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
                loadingHelper.hideProgress()
            }.onFailure {
                apiStatus.value = ApiResult.Failed(Exception(it.message.toString()))
                Log.d("SettingViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }


        }
    }

    fun setPushSetting(alarmType: AlarmType, checked: Boolean) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                val userToken = context.userTokenStore.data.map {
                    it.token
                }.first()
                val userId = context.userIdStore.data.map {
                    it.userId
                }.first()
                apiStatus.value = ApiResult.Loading
                myPageRepository.setPushSetting(
                    userToken = userToken,
                    userId = userId,
                    alarmType = alarmType
                )
            }.onSuccess {
                apiStatus.value = ApiResult.Success("Success")
                when (it.result.code) {
                    ResultCodeStatus.SuccessWithNoData.code -> {
                        Log.d("SettingViewModel", "Success")
                        when (alarmType) {
                            AlarmType.day_alarm -> {
                                if (checked) {
                                    _timeEvent.emit(true)
                                    FirebaseMessaging.getInstance()
                                        .subscribeToTopic(AlarmType.time_event.name)
                                } else {
                                    _timeEvent.emit(false)
                                    FirebaseMessaging.getInstance()
                                        .unsubscribeFromTopic(AlarmType.time_event.name)
                                }
                                _dayEvent.emit(!_dayEvent.value)

                            }
                            AlarmType.time_event -> {
                                if (checked) {
                                    _dayEvent.emit(true)
                                    FirebaseMessaging.getInstance()
                                        .unsubscribeFromTopic(AlarmType.day_alarm.name)
                                }
                                _timeEvent.emit(!_timeEvent.value)

                            }
                            AlarmType.night_alarm -> {
                                if (checked) {
                                    _newVotes.emit(true)
                                    _freeVotes.emit(true)
                                    FirebaseMessaging.getInstance()
                                        .subscribeToTopic(AlarmType.new_votes.name)
                                    FirebaseMessaging.getInstance()
                                        .subscribeToTopic(AlarmType.free_tickets_gone.name)
                                } else {
                                    _newVotes.emit(false)
                                    _freeVotes.emit(false)
                                    FirebaseMessaging.getInstance()
                                        .unsubscribeFromTopic(AlarmType.new_votes.name)
                                    FirebaseMessaging.getInstance()
                                        .unsubscribeFromTopic(AlarmType.free_tickets_gone.name)
                                }
                                _nightEvent.emit(!_nightEvent.value)
                            }
                            AlarmType.new_votes -> {
                                if (checked) {
                                    _nightEvent.emit(true)
                                    FirebaseMessaging.getInstance()
                                        .subscribeToTopic(AlarmType.night_alarm.name)
                                }
                                _newVotes.emit(!_newVotes.value)
                            }
                            AlarmType.free_tickets_gone -> {
                                if (checked) {
                                    _nightEvent.emit(true)
                                    FirebaseMessaging.getInstance()
                                        .subscribeToTopic(AlarmType.night_alarm.name)
                                }
                                _freeVotes.emit(!_freeVotes.value)
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
                loadingHelper.hideProgress()
            }.onFailure {
                Log.d("SettingViewModel", it.message.toString())
                apiStatus.value = ApiResult.Failed(Exception(it.message.toString()))
                loadingHelper.hideProgress()
            }
        }
    }

    fun signOut(reason: Int, etc: String? = null) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    myPageRepository.signOut(
                        userToken = userLocalToken.value?.token ?: "",
                        userId = it.userId,
                        reason = reason,
                        etc = etc
                    )
                }.onSuccess {
                    _secessionConfirmDialogState.emit(true)
                    loadingHelper.hideProgress()
                }.onFailure {
                    _secessionConfirmDialogState.emit(false)
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun changeSecessionConfirmDialogState(boolean: Boolean) {
        viewModelScope.launch {
            _secessionConfirmDialogState.emit(boolean)
        }

    }

}