package com.trotfan.trot.ui.home.ranking.history

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.ranking.history.component.monthly.EmptyMonthlyRankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingHistoryBanner
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingHistoryMonthlyHeader
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingStarMonthlyItem
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import com.trotfan.trot.ui.theme.FanwooriTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RankingHistoryMonthly(
    emptyState: Boolean = false,
    onCalenderClick: (String) -> Unit,
    onItemClick: () -> Unit,
    viewModel: RankingHistoryViewModel = viewModel()
) {
    val manList = viewModel.manList.collectAsState().value
    val womanList = viewModel.womanList.collectAsState().value
    val favoriteStarGender by viewModel.userInfoManager.favoriteStarGenderFlow.collectAsState(
        initial = Gender.MEN
    )
    var genderIndex by remember {
        mutableStateOf(if (favoriteStarGender == Gender.MEN) 0 else 1)
    }
    val coroutineScope = rememberCoroutineScope()

    Column {
        if (emptyState) {
            EmptyMonthlyRankingHistory()
        } else {
            Spacer(modifier = Modifier.height(24.dp))

            RankingHistoryMonthlyHeader(onCalenderClick = {
                onCalenderClick(it)
            }, onGenderClick = {
                genderIndex = it
            }, onCalenderAfter = {
                coroutineScope.launch {
                    if (viewModel.monthlyMonth.value == 12) {
                        viewModel.monthlyYear.emit(viewModel.monthlyYear.value.plus(1))
                        viewModel.monthlyMonth.emit(1)
                    } else {
                        viewModel.monthlyMonth.emit(viewModel.monthlyMonth.value.plus(1))
                    }
                    settingDateRange(viewModel, coroutineScope)
                    viewModel.getMonthlyStarRankingList()
                }
            }, onCalenderBefore = {
                coroutineScope.launch {
                    if (viewModel.monthlyMonth.value == 1) {
                        viewModel.monthlyYear.emit(viewModel.monthlyYear.value.minus(1))
                        viewModel.monthlyMonth.emit(12)
                    } else {
                        viewModel.monthlyMonth.emit(viewModel.monthlyMonth.value.minus(1))
                    }
                    settingDateRange(viewModel, coroutineScope)
                    viewModel.getMonthlyStarRankingList()
                }
            })

            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                item {
                    RankingHistoryBanner()
                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (genderIndex == 0) {
                    items(manList.size) { idx ->
                        RankingStarMonthlyItem(star = manList[idx]) {
                            onItemClick()
                        }
                    }
                } else {
                    items(womanList.size) { idx ->
                        RankingStarMonthlyItem(star = womanList[idx]) {
                            onItemClick()
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

fun settingDateRange(viewModel: RankingHistoryViewModel, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        viewModel.isEnded.emit(
            "${viewModel.monthlyYear.value}-${viewModel.monthlyMonth.value}" == viewModel.datePickerRange.value?.ended_at?.substring(
                0,
                7
            ).toString()
        )
        viewModel.isStared.emit(
            "${viewModel.monthlyYear.value}-${viewModel.monthlyMonth.value}" == viewModel.datePickerRange.value?.started_at?.substring(
                0,
                7
            ).toString()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun RankingHistoryMonthlyPreview() {
    FanwooriTheme {
        RankingHistoryMonthly(
            onCalenderClick = {},
            onItemClick = {}
        )
    }
}