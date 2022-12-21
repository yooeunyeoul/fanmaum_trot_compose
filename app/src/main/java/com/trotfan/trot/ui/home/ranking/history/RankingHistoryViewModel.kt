package com.trotfan.trot.ui.home.ranking.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RankingHistoryViewModel @Inject constructor() : ViewModel() {
    private val calender: Calendar = Calendar.getInstance()
    val monthlyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val monthlyMonth = MutableStateFlow(calender.get(Calendar.MONTH))
    val dailyYear = MutableStateFlow(calender.get(Calendar.YEAR))
    val dailyMonth = MutableStateFlow(calender.get(Calendar.MONTH) + 1)
    val dailyDay = MutableStateFlow(calender.get(Calendar.DAY_OF_MONTH))

    init {
        yesterday(calender)
    }

    private fun yesterday(calendar: Calendar) {
        val simpleDateFormat = SimpleDateFormat("dd", Locale.KOREA)
        calendar.add(Calendar.DATE, -1)
        viewModelScope.launch {
            dailyDay.emit(simpleDateFormat.format(calendar.time).toInt())
        }
    }
}