package com.trotfan.trot.ui.home.ranking.history.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.RankingHistoryRepository
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RankingHistoryViewModel @Inject constructor(
    private val repository: RankingHistoryRepository,
    application: Application
) : AndroidViewModel(application) {
    private val calender: Calendar = Calendar.getInstance()
    val monthlyYear = MutableStateFlow(2023)
    val monthlyMonth = MutableStateFlow(1)
    val dailyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val dailyMonth = MutableStateFlow(calender.get(Calendar.MONTH) + 1)
    val dailyDay = MutableStateFlow(calender.get(Calendar.DAY_OF_MONTH))

    val monthlyManList = MutableStateFlow(listOf<StarRanking>())
    val monthlyWomanList = MutableStateFlow(listOf<StarRanking>())
    val datePickerRange = MutableStateFlow<DatePickerRange?>(null)

    val dailyManList = MutableStateFlow(listOf<StarRankingDaily>())
    val dailyWomanList = MutableStateFlow(listOf<StarRankingDaily>())

    private val context = getApplication<Application>()
    var userInfoManager: UserInfoManager = UserInfoManager(context.FavoriteStarDataStore)
    val isStared = MutableStateFlow(false)
    val isEnded = MutableStateFlow(true)

    val banners = MutableStateFlow<List<Banner>?>(null)
    val startYear = MutableStateFlow(2022)

    val monthlyGender = MutableStateFlow(Gender.MEN)
    val dailyGender = MutableStateFlow(Gender.MEN)

    init {
        getDatePickerRange()
        getBannerList()
        getMonthlyStarRankingList()
        observeGender()
    }

    private fun observeGender() {
        viewModelScope.launch {
            userInfoManager.favoriteStarGenderFlow.collectLatest {
                monthlyGender.emit(it ?: Gender.MEN)
                dailyGender.emit(it ?: Gender.MEN)
            }
        }
    }

    fun getBannerList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getBanners("last", "aos")
            }.onSuccess {
                banners.emit(it.data)
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
            }
        }
    }

    fun getMonthlyStarRankingList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMonthlyStarList(
                    monthlyYear.value.toString(),
                    monthlyMonth.value.toString()
                )
            }.onSuccess {
                it.data?.men?.let { mans ->
                    monthlyManList.emit(mans)
                }
                it.data?.women?.let { womans ->
                    monthlyWomanList.emit(womans)
                }
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
            }
        }
    }

    fun getDailyStarRankingList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getDailyStarList(
                    dailyYear.value.toString(),
                    dailyMonth.value.toString(),
                    dailyDay.value.toString()
                )
            }.onSuccess {
                it.data?.men?.let { mans ->
                    dailyManList.emit(mans)
                }
                it.data?.women?.let { womans ->
                    dailyWomanList.emit(womans)
                }
            }.onFailure {

            }
        }
    }

    fun getDatePickerRange() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getDatePickerRange()
            }.onSuccess {
                it.data?.let { data ->
                    datePickerRange.emit(data)
                    startYear.emit(data.started_at.split("-")[0].toInt())
                    val tempDate = data.ended_at.split("-")
                    monthlyYear.emit(tempDate[0].toInt())
                    monthlyMonth.emit(tempDate[1].toInt())
                    dailyYear.emit(tempDate[0].toInt())
                    dailyMonth.emit(tempDate[1].toInt())
                    dailyDay.emit(tempDate[2].toInt())
                    getDailyStarRankingList()
                }
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
            }
        }
    }


    fun settingDateRange() {
        viewModelScope.launch {
            val tempMonth = if (monthlyMonth.value < 10) {
                "0${monthlyMonth.value}"
            } else {
                monthlyMonth.value
            }
            isEnded.emit(
                "${monthlyYear.value}-${tempMonth}" == datePickerRange.value?.ended_at?.substring(
                    0,
                    7
                ).toString()
            )
            isStared.emit(
                "${monthlyYear.value}-${tempMonth}" == datePickerRange.value?.started_at?.substring(
                    0,
                    7
                ).toString()
            )
        }
    }
//
//    private fun yesterday(calendar: Calendar) {
//        val simpleDateFormat = SimpleDateFormat("dd", Locale.KOREA)
//        calendar.add(Calendar.DATE, -1)
//        viewModelScope.launch {
//            dailyDay.emit(simpleDateFormat.format(calendar.time).toInt())
//        }
//    }
}