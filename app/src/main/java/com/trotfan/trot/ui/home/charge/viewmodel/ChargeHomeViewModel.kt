package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.Expired
import com.trotfan.trot.model.MissionState
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.ChargeRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChargeHomeViewModel @Inject constructor(
    private val repository: ChargeRepository,
    application: Application,
    private val loadingHelper: LoadingHelper
) : BaseViewModel(application) {

    private val context = getApplication<Application>()
    lateinit var userInfoManager: UserInfoManager
    lateinit var userTicketManager: UserTicketManager
    val missionState: StateFlow<MissionState?>
        get() = _missionState
    private val _missionState = MutableStateFlow<MissionState?>(null)

    val attendanceState: StateFlow<Boolean>
        get() = _attendanceState
    private val _attendanceState =
        MutableStateFlow(_missionState.value?.missions?.attendance ?: false)

    val starShareState: StateFlow<Boolean>
        get() = _starShareState
    private val _starShareState =
        MutableStateFlow(_missionState.value?.missions?.star_share ?: false)

    val videoRewardState: StateFlow<Boolean>
        get() = _videoRewardState
    private val _videoRewardState =
        MutableStateFlow(_missionState.value?.missions?.video_reward ?: false)

    val rouletteState: StateFlow<Boolean>
        get() = _rouletteState
    private val _rouletteState =
        MutableStateFlow(_missionState.value?.missions?.roulette ?: false)

    val missionCompleteCount: StateFlow<Int>
        get() = _missionCompleteCount
    private val _missionCompleteCount =
        MutableStateFlow(0)

    private val maxAdCount = 20
    val videoCount: StateFlow<Int>
        get() = _videoCount
    private val _videoCount =
        MutableStateFlow(maxAdCount.minus(_missionState.value?.remaining?.video_reward ?: 0))

    val starName: StateFlow<String>
        get() = _starName
    private val _starName =
        MutableStateFlow("")

    val rewardedState: StateFlow<Int>
        get() = _rewardedState
    private val _rewardedState =
        MutableStateFlow(0)

    val lastApiTime: StateFlow<String>
        get() = _lastApiTime
    private val _lastApiTime =
        MutableStateFlow("")

    val missionSnackBarState = MutableStateFlow(false)
    val attendanceRewardDialogState = MutableStateFlow(false)

    init {
        if (_missionState.value == null) {
            getMissions()
        }
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
            _starName.emit(userInfoManager.favoriteStarNameFlow.first() ?: "")
        }
    }

    fun getVoteTickets(purchaseHelper: PurchaseHelper) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.getVoteTickets(
                        userId = it.userId,
                        userToken = userLocalToken.value?.token ?: ""
                    )

                }.onSuccess { response ->
                    when (response.result.code) {
                        ResultCodeStatus.SuccessWithData.code -> {
                            response.data?.let { it1 -> purchaseHelper.refreshTickets(it1) }
                            purchaseHelper.closeApiCall()
                        }

                        ResultCodeStatus.Fail.code -> {
                            Log.e("ChargeHomeViewModel", response.result.message)
                        }
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    Log.e("ChargeHomeViewModel", it.message.toString())
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun getMissions() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.getMissions(
                        userToken = it.token
                    )
                }.onSuccess {
                    _missionState.emit(it.data)
                    _videoCount.emit(maxAdCount - (it.data?.remaining?.video_reward ?: 0))
                    var count = 0
                    it.data?.missions?.let { missions ->
                        _attendanceState.emit(missions.attendance)
                        _starShareState.emit(missions.star_share)
                        _videoRewardState.emit(missions.video_reward)
                        _rouletteState.emit(missions.roulette)
                        if (missions.attendance) count++
                        if (missions.star_share) count++
                        if (missions.video_reward) count++
                        if (missions.roulette) count++
                    }
                    _missionCompleteCount.emit(count)

                    if (it.data?.rewarded == true) {
                        _rewardedState.emit(2)
                    } else if (count == 4) {
                        _rewardedState.emit(1)
                    } else {
                        _rewardedState.emit(0)
                    }

                    val simpleDateFormat = SimpleDateFormat("dd")
                    val dateString = simpleDateFormat.format(Date()).toString()
                    _lastApiTime.emit(dateString)
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postRewardVideo() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postRewardVideo(
                        userToken = it.token
                    )
                }.onSuccess {
                    _videoCount.emit(_videoCount.value.plus(1))
                    userTicketManager.storeUserTicket(
                        it.data?.tickets?.unlimited ?: 0,
                        it.data?.tickets?.limited ?: 0
                    )
                    if (!_videoRewardState.value) {
                        _videoRewardState.emit(true)
                        missionSnackBarState.emit(true)
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postAttendance() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postAttendance(it.token)
                }.onSuccess {
                    _attendanceState.emit(true)
                    missionSnackBarState.emit(true)
                    attendanceRewardDialogState.emit(true)
                    userTicketManager.storeUserTicket(
                        it.data?.unlimited ?: 0,
                        it.data?.limited ?: 0
                    )
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postShareStar() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postShareStar(it.token)
                }.onSuccess {
                    if (_starShareState.value.not()) {
                        _starShareState.emit(true)
                        missionSnackBarState.emit(true)
                        _missionCompleteCount.emit(_missionCompleteCount.value.plus(1))
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }
}