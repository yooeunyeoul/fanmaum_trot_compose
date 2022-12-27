package com.trotfan.trot.ui.home.ranking.history.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.model.Banner
import com.trotfan.trot.model.DatePickerRange
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.model.StarRankingDetail
import com.trotfan.trot.repository.RankingHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RankingHistoryViewModel @Inject constructor(
    private val repository: RankingHistoryRepository,
    application: Application
) : AndroidViewModel(application) {
    private val calender: Calendar = Calendar.getInstance()
    val monthlyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val monthlyMonth = MutableStateFlow(calender.get(Calendar.MONTH) + 1)
    val dailyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val dailyMonth = MutableStateFlow(calender.get(Calendar.MONTH) + 1)
    val dailyDay = MutableStateFlow(calender.get(Calendar.DAY_OF_MONTH))

    val monthlyManList = MutableStateFlow(listOf<StarRanking>())
    val monthlyWomanList = MutableStateFlow(listOf<StarRanking>())
    val datePickerRange = MutableStateFlow<DatePickerRange?>(null)

    val dailyManList = MutableStateFlow(listOf<StarRanking>())
    val dailyWomanList = MutableStateFlow(listOf<StarRanking>())

    private val context = getApplication<Application>()
    var userInfoManager: UserInfoManager = UserInfoManager(context.FavoriteStarDataStore)
    val isStared = MutableStateFlow(false)
    val isEnded = MutableStateFlow(true)

    val banners = MutableStateFlow<List<Banner>?>(null)
    val startYear = MutableStateFlow(2000)

    val starRankingDetail = MutableStateFlow(listOf<StarRankingDetail>())

    init {
        getDatePickerRange()
        getBannerList()
        getMonthlyStarRankingList()
    }

    fun getBannerList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getBanners("rank", "aos")
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
                    startYear.emit(2022)
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

    fun getStarRankingDetail(starId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getStarRankingDetail(starId)
            }.onSuccess {
                it.data?.let { list ->
                    starRankingDetail.emit(list)
                }
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
            }
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