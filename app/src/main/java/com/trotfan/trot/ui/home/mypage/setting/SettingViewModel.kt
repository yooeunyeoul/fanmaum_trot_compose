package com.trotfan.trot.ui.home.mypage.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.Alarm
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.MyPageRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    private val _finishState =
        MutableStateFlow(false)
    val finishState = _finishState.asStateFlow()

    private val _toastText =
        MutableStateFlow("")
    val toastText = _toastText.asStateFlow()

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
                Log.d("SettingViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }


        }
    }

    fun changeAlarmSetting(alarmType: AlarmType, checked: Boolean) {
        viewModelScope.launch {
            when (alarmType) {
                AlarmType.day_alarm -> {
                    if (checked) {
                        _timeEvent.emit(true)
                        _toastText.emit("${LocalDate.now().year}.${LocalDate.now().month.value}.${LocalDate.now().dayOfMonth}\n주간 광고/이벤트 정보 수신 동의")
                    } else {
                        _timeEvent.emit(false)
                        _toastText.emit("${LocalDate.now().year}.${LocalDate.now().month.value}.${LocalDate.now().dayOfMonth}\n주간 광고/이벤트 정보 수신 거부")
                    }
                    _dayEvent.emit(!_dayEvent.value)
                }
                AlarmType.time_event -> {
                    if (dayEvent.value) {
                        _timeEvent.emit(!_timeEvent.value)
                    }
                }
                AlarmType.night_alarm -> {
                    if (checked) {
                        _newVotes.emit(true)
                        _freeVotes.emit(true)
                        _toastText.emit("${LocalDate.now().year}.${LocalDate.now().month.value}.${LocalDate.now().dayOfMonth}\n야간 광고/이벤트 정보 수신 동의")
                    } else {
                        _newVotes.emit(false)
                        _freeVotes.emit(false)
                        _toastText.emit("${LocalDate.now().year}.${LocalDate.now().month.value}.${LocalDate.now().dayOfMonth}\n야간 광고/이벤트 정보 수신 거부")
                    }
                    _nightEvent.emit(!_nightEvent.value)
                }
                AlarmType.new_votes -> {
                    if (nightEvent.value) {
                        _newVotes.emit(!_newVotes.value)
                    }
                }
                AlarmType.free_tickets_gone -> {
                    if (nightEvent.value) {
                        _freeVotes.emit(!_freeVotes.value)
                    }

                }
            }

        }
    }

    fun setPushSetting() {
        viewModelScope.launch {
            loadingHelper.showProgress()
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
                    alarm = Alarm(
                        dayAlarm = dayEvent.value,
                        freeTicketsGone = freeVotes.value,
                        newVotes = newVotes.value,
                        nightAlarm = nightEvent.value,
                        timeEvent = timeEvent.value
                    )
                )
            }.onSuccess {
                if (dayEvent.value) {
                    FirebaseMessaging.getInstance()
                        .subscribeToTopic(AlarmType.day_alarm.name)
                } else {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(AlarmType.day_alarm.name)
                }
                if (freeVotes.value) {
                    FirebaseMessaging.getInstance()
                        .subscribeToTopic(AlarmType.free_tickets_gone.name)
                } else {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(AlarmType.free_tickets_gone.name)
                }
                if (newVotes.value) {
                    FirebaseMessaging.getInstance()
                        .subscribeToTopic(AlarmType.new_votes.name)
                } else {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(AlarmType.new_votes.name)
                }
                if (nightEvent.value) {
                    FirebaseMessaging.getInstance()
                        .subscribeToTopic(AlarmType.night_alarm.name)
                } else {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(AlarmType.night_alarm.name)
                }
                if (timeEvent.value) {
                    FirebaseMessaging.getInstance()
                        .subscribeToTopic(AlarmType.time_event.name)
                } else {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(AlarmType.time_event.name)
                }
                _finishState.emit(true)
                loadingHelper.hideProgress()

            }.onFailure {
                Log.d("SettingViewModel", it.message.toString())
                _finishState.emit(true)
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