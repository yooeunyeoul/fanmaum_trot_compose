package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fanmaum.roullete.SpinWheelVisibleState
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.R
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.LuckyTicket
import com.trotfan.trot.repository.ChargeRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.utils.convertStringToTime
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val repository: ChargeRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()

    private val fourHourMilliSecond = 14400
    private val _luckyTicket =
        MutableStateFlow<LuckyTicket?>(
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


    init {
        getRoulette()
    }

    fun getRoulette() {
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
                    settingRoulette()
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }

        }
    }

    private fun settingRoulette() {
        val luckyTicket = _luckyTicket.value
        when {
            luckyTicket?.rewarded_at == null || luckyTicket.today.max == luckyTicket.today.remaining -> {
                _visibleState.value = SpinWheelVisibleState.Available
            }

            else -> {
                val remainTime = getTime(
                    convertStringToTime(luckyTicket.rewarded_at),
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
        Log.e("roullete", roulette.toString())

        _resultDegree.value = roulette?.degree ?: 30f
    }

    private fun checkRemainTime(remainTime: Int) {
        var remainTime = fourHourMilliSecond - remainTime

        viewModelScope.launch {
            while (true) {
                Log.e("얘 멈춘거 맞지?", "맞아??")
                delay(1_000)
                remainTime -= 1
                if (remainTime < 0) {
                    Log.e("여기 찍히는거 맞나??", "맞아??")
                    getRoulette()
                    break

                } else {
                    val day = remainTime / (24 * 60 * 60)

                    when {
                        day == 0 -> {
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
                        day > 0 -> {
                            _remainTime.emit(
                                "남은 일수가 1보다 큰데요??"
                            )
                        }
                    }
                }


            }
        }
    }

    fun callApi() {
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
                    _luckyTicket.emit(it.data)
                    settingRoulette()
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
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