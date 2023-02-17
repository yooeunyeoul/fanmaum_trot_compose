package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fanmaum.roullete.SpinWheelVisibleState
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.R
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.di.ApiResult
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

enum class TicketKind(val quantity: Int, val icon: Int, val degree: Float) {
    TenThousand(quantity = 10000, icon = R.drawable.charge_roulette10000, degree = 330f),
    Thousand(quantity = 1000, icon = R.drawable.charge_roulette1000, degree = 270f),
    TwoHundred(quantity = 200, icon = R.drawable.charge_roulette200, degree = 210f),
    FiveHundred(quantity = 500, icon = R.drawable.charge_roulette500, degree = 150f),
    TwoHundred2(quantity = 200, icon = R.drawable.charge_roulette200, degree = 90f),
    FiveHundred2(quantity = 500, icon = R.drawable.charge_roulette500, degree = 30f)

}

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val repository: ChargeRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()

    private val fourHourMilliSecond = 14400
    private val _luckyTicket =
        MutableStateFlow(
            LuckyTicket(
                chance = 3,
                lastTime = "2023-02-13 11:18:59",
                rouletteQuantity = 1000,
                userId = 92
            )
        )
    val luckyTicket = _luckyTicket.asStateFlow()

    private val _visibleState =
        MutableStateFlow(
            SpinWheelVisibleState.Available
        )
    val visibleState = _visibleState.asStateFlow()

    private val _resultDegree =
        MutableStateFlow(
            30f
        )
    val resultDegree = _resultDegree.asStateFlow()

    private val _remainTime = MutableStateFlow("00:00:00")
    val remainingTime = _remainTime.asStateFlow()

    init {
        testRoulette()
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
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }

        }
    }

    fun testRoulette() {
        val dd = getTime(
            convertStringToTime(_luckyTicket.value.lastTime),
            application = context as BaseApplication
        )
        Log.e("남은 시간 ", dd.toString())

        if (_luckyTicket.value.chance > 0) {
            if (dd > fourHourMilliSecond) {
                _visibleState.value = SpinWheelVisibleState.Available
            } else {
                _visibleState.value = SpinWheelVisibleState.Waiting
                checkRemainTime(dd)
            }
        } else {
            _visibleState.value = SpinWheelVisibleState.UnAvailable
        }

        val roulette =
            TicketKind.values().filter { it.quantity == _luckyTicket.value.rouletteQuantity }
                .firstOrNull()

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
                    testRoulette()
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

}