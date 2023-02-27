package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fanmaum.roullete.SpinWheelVisibleState
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.BaseTicket
import com.trotfan.trot.model.GetLuckyTicket
import com.trotfan.trot.model.MissionState
import com.trotfan.trot.model.PostLuckyTicket
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.ChargeRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.utils.convertStringToTime
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

enum class TicketKind(val quantity: Int, val icon: Int, val degree: Float) {
    TenThousand(quantity = 10000, icon = R.drawable.charge_roulette10000, degree = 30f),
    Thousand(quantity = 1000, icon = R.drawable.charge_roulette1000, degree = 90f),
    TwoHundred(quantity = 200, icon = R.drawable.charge_roulette200, degree = 150f),
    FiveHundred(quantity = 500, icon = R.drawable.charge_roulette500, degree = 210f),
    TwoHundred2(quantity = 200, icon = R.drawable.charge_roulette200, degree = 270f),
    FiveHundred2(quantity = 500, icon = R.drawable.charge_roulette500, degree = 330f)
}

enum class MissionRewardState {
    Incomplete, Complete, Rewarded
}

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
        MutableStateFlow(_missionState.value?.missions?.share ?: false)

    val videoRewardState: StateFlow<Boolean>
        get() = _videoRewardState
    private val _videoRewardState =
        MutableStateFlow(_missionState.value?.missions?.video ?: false)

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
        MutableStateFlow(maxAdCount.minus(_missionState.value?.remaining?.video ?: 0))

    val starName: StateFlow<String>
        get() = _starName
    private val _starName =
        MutableStateFlow("")

    val rewardedState: StateFlow<MissionRewardState>
        get() = _rewardedState
    private val _rewardedState =
        MutableStateFlow(MissionRewardState.Incomplete)

    val lastApiTime: StateFlow<String>
        get() = _lastApiTime
    private val _lastApiTime =
        MutableStateFlow("")

    private val fourHourMilliSecond = 14400
    private val _luckyTicket =
        MutableStateFlow<BaseTicket?>(
            null
        )
    val luckyTicket = _luckyTicket.asStateFlow()

    private val _visibleState =
        MutableStateFlow(
            SpinWheelVisibleState.UnAvailable
        )
    val visibleState = _visibleState.asStateFlow()

    private val _resultDegree =
        MutableStateFlow<Float?>(
            null
        )
    val resultDegree = _resultDegree.asStateFlow()

    private val _remainTime = MutableStateFlow("00:00:00")
    val remainingTime = _remainTime.asStateFlow()

    private val _rewardDialogShowing = MutableStateFlow(false)
    val rewardDialogShowing = _rewardDialogShowing.asStateFlow()

    val rouletteCount: StateFlow<Int>
        get() = _rouletteCount
    private val _rouletteCount = MutableStateFlow(0)

    val missionSnackBarState = MutableStateFlow(false)
    val attendanceRewardDialogState = MutableStateFlow(false)
    val missionRewardDialogState = MutableStateFlow(false)
    val missionCompleteChargePopupState = MutableStateFlow(false)

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    init {
        if (_missionState.value == null) {
            getMissions()
        }
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
            _starName.emit(userInfoManager.favoriteStarNameFlow.first() ?: "")
//            _unlimitedTicket.emit(userTicketManager.expiredUnlimited.first() ?: 0)
//            _todayTicket.emit(userTicketManager.expiredToday.first() ?: 0)
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
                            userTicketManager.storeUserTicket(
                                response.data?.unlimited ?: 0,
                                response.data?.limited ?: 0
                            )
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
                    _videoCount.emit(maxAdCount - (it.data?.remaining?.video ?: 0))
                    _rouletteCount.emit(it.data?.remaining?.roulette ?: 0)
                    var count = 0
                    it.data?.missions?.let { missions ->
                        _attendanceState.emit(missions.attendance)
                        _starShareState.emit(missions.share)
                        _videoRewardState.emit(missions.video)
                        _rouletteState.emit(missions.roulette)
                        if (missions.attendance) count++
                        if (missions.share) count++
                        if (missions.video) count++
                        if (missions.roulette) count++
                    }
                    _missionCompleteCount.emit(count)

                    if (it.data?.rewarded == true) {
                        _rewardedState.emit(MissionRewardState.Rewarded)
                    } else if (count == 4) {
                        _rewardedState.emit(MissionRewardState.Complete)
                    } else {
                        _rewardedState.emit(MissionRewardState.Incomplete)
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

    fun getRoulette() {
        if (job.isActive) {
            job.cancel()
        }
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collectLatest {
                kotlin.runCatching {
                    repository.checkRoulette(
                        userToken = userLocalToken.value?.token ?: "",
                        userId = it.userId.toInt()
                    )
                }.onSuccess {
                    _luckyTicket.emit(it.data)
                    _rouletteCount.emit(it.data?.today?.remaining ?: 0)
                    settingRoulette()
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }

        }
    }

    private fun settingRoulette() {
        lateinit var luckyTicket: BaseTicket
        when {
            _luckyTicket.value is GetLuckyTicket -> {
                luckyTicket = _luckyTicket.value as GetLuckyTicket
            }
            _luckyTicket.value is PostLuckyTicket -> {
                luckyTicket = _luckyTicket.value as PostLuckyTicket
            }
        }

        when {
            luckyTicket?.rewarded_at == null || luckyTicket.today.max == luckyTicket.today.remaining -> {
                _visibleState.value = SpinWheelVisibleState.Available
            }

            else -> {
                val remainTime = getTime(
                    convertStringToTime(luckyTicket.rewarded_at ?: ""),
                    application = context as BaseApplication
                )
                Log.e("남은 시간 ", remainTime.toString())
                if (luckyTicket.today.remaining > 0) {
                    if (remainTime >= fourHourMilliSecond) {
                        _visibleState.value = SpinWheelVisibleState.Available
                    } else {
                        _visibleState.value = SpinWheelVisibleState.Waiting
                        checkRemainTime(remainTime)
                    }

                } else {
                    _visibleState.value = SpinWheelVisibleState.UnAvailable
                }
            }
        }
        val roulette =
            TicketKind.values().filter { it.quantity == luckyTicket?.next_reward }
                .firstOrNull()

        _resultDegree.value = roulette?.degree ?: 30f
    }

    private fun checkRemainTime(remainTime: Int) {
        var remainTime = fourHourMilliSecond - remainTime

        viewModelScope.launch(job) {
            while (true) {
//                Log.e("얘 멈춘거 맞지?", "맞아??")
                delay(1_000)
                remainTime -= 1
                if (remainTime < 0) {
//                    Log.e("여기 찍히는거 맞나??", "맞아??")
                    getRoulette()
                    break

                } else {
                    val day = remainTime / (24 * 60 * 60)

                    when (day) {
                        0 -> {
                            val hour = remainTime / (60 * 60)
                            val minute = remainTime / 60 % 60
                            val second = remainTime % 60
                            var hourString = ""
                            var minuteString = ""
                            var secondString = ""
                            hourString = if (hour < 10) "0${hour}" else "$hour"
                            minuteString = if (minute < 10) "0${minute}" else "$minute"
                            secondString = if (second < 10) "0${second}" else "$second"

                            _remainTime.emit(
                                "${hourString}:${minuteString}:${secondString}"
                            )
                        }
                        else -> {
//                            _remainTime.emit(
//                                "남은 일수가 1보다 큰데요??"
//                            )
                        }
                    }
                }


            }
        }
    }

    fun postRewardRoulette() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collectLatest {
                kotlin.runCatching {
                    repository.rewardRoulette(
                        userToken = userLocalToken.value?.token ?: "",
                        userId = it.userId.toInt()
                    )
                }.onSuccess {
                    hideRewardDialog()
                    val data = it.data as PostLuckyTicket
                    _luckyTicket.emit(data)
                    userTicketManager.storeUserTicket(
                        data.tickets?.unlimited ?: 0,
                        data.tickets?.limited ?: 0
                    )
                    if (!_rouletteState.value) {
                        _rouletteState.emit(true)
                        missionSnackBarState.emit(true)
                        _missionCompleteCount.emit(_missionCompleteCount.value.plus(1))
                        checkMissionState()
                    }
                    _rouletteCount.emit(_rouletteCount.value.minus(1))
                    settingRoulette()
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
                        _missionCompleteCount.emit(_missionCompleteCount.value.plus(1))
                        checkMissionState()
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
                    _missionCompleteCount.emit(_missionCompleteCount.value.plus(1))
                    checkMissionState()
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postMission() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postMission(it.token)
                }.onSuccess {
                    missionRewardDialogState.emit(true)
                    userTicketManager.storeUserTicket(
                        it.data?.unlimited ?: 0,
                        it.data?.limited ?: 0
                    )
                    _rewardedState.emit(MissionRewardState.Rewarded)
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postShareStar(isMissionPage: Boolean = false) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postShareStar(it.token)
                }.onSuccess {
                    if (_starShareState.value.not()) {
                        _starShareState.emit(true)
                        if (isMissionPage.not()) {
                            missionSnackBarState.emit(true)
                        }
                        _missionCompleteCount.emit(_missionCompleteCount.value.plus(1))
                        checkMissionState()
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun checkMissionState() {
        viewModelScope.launch {
            if (_missionCompleteCount.value == 4) {
                _rewardedState.emit(MissionRewardState.Complete)
            }
        }
    }

    fun hideRewardDialog() {
        viewModelScope.launch {
            _rewardDialogShowing.emit(false)
        }

    }

    fun showRewardDialog() {
        viewModelScope.launch {
            _rewardDialogShowing.emit(true)
        }
    }
}