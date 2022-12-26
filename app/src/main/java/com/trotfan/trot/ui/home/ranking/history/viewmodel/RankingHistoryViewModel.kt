package com.trotfan.trot.ui.home.ranking.history.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.model.DatePickerRange
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.repository.RankingHistoryRepository
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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

    val manList = MutableStateFlow(listOf<StarRanking>())
    val womanList = MutableStateFlow(listOf<StarRanking>())
    val datePickerRange = MutableStateFlow<DatePickerRange?>(null)

    private val context = getApplication<Application>()
    var userInfoManager: UserInfoManager = UserInfoManager(context.FavoriteStarDataStore)
    val isStared = MutableStateFlow(false)
    val isEnded = MutableStateFlow(true)

    init {
        getDatePickerRange()
        getMonthlyStarRankingList()
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
                    manList.emit(mans)
                }
                it.data?.women?.let { womans ->
                    womanList.emit(womans)
                }
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
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
                    val tempDate = data.ended_at.split("-")
                    monthlyYear.emit(tempDate[0].toInt())
                    monthlyMonth.emit(tempDate[1].toInt())
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