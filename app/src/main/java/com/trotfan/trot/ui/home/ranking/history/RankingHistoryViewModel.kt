package com.trotfan.trot.ui.home.ranking.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.repository.RankingHistoryRepository
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
    val monthlyMonth = MutableStateFlow(calender.get(Calendar.MONTH))
    val dailyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val dailyMonth = MutableStateFlow(calender.get(Calendar.MONTH) + 1)
    val dailyDay = MutableStateFlow(calender.get(Calendar.DAY_OF_MONTH))
    

    var userInfoManager: UserInfoManager
    private val context = getApplication<Application>()

    init {
        userInfoManager = UserInfoManager(context.FavoriteStarDataStore)
        yesterday(calender)
    }

    fun getMonthlyStarRankingList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMonthlyStarList(
                    monthlyYear.value.toString(),
                    monthlyMonth.value.toString()
                )
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    private fun yesterday(calendar: Calendar) {
        val simpleDateFormat = SimpleDateFormat("dd", Locale.KOREA)
        calendar.add(Calendar.DATE, -1)
        viewModelScope.launch {
            dailyDay.emit(simpleDateFormat.format(calendar.time).toInt())
        }
    }
}