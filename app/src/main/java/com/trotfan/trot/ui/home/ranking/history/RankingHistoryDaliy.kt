package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.ui.home.ranking.history.component.daily.EmptyDailyRankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingHistoryDailyHeader
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingStarDailyItem
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.Gender

@Composable
fun RankingHistoryDaily(
    emptyState: Boolean = false,
    onCalenderClick: () -> Unit,
    viewModel: RankingHistoryViewModel = viewModel()
) {
    val manList = viewModel.dailyManList.collectAsState().value
    val womanList = viewModel.dailyWomanList.collectAsState().value
    val favoriteStarGender by viewModel.userInfoManager.favoriteStarGenderFlow.collectAsState(
        initial = Gender.MEN
    )
    var genderIndex by remember {
        mutableStateOf(if (favoriteStarGender == Gender.MEN) 0 else 1)
    }
    val coroutineScope = rememberCoroutineScope()

    Column {
        if (emptyState) {
            EmptyDailyRankingHistory()
        } else {
            Spacer(modifier = Modifier.height(24.dp))
            RankingHistoryDailyHeader(onCalenderClick = {
                onCalenderClick()
            }, onGenderClick = {
                genderIndex = it
            })

            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                if (genderIndex == 0) {
                    items(manList.size) { idx ->
                        RankingStarDailyItem(star = manList[idx])
                    }
                } else {
                    items(womanList.size) { idx ->
                        RankingStarDailyItem(star = womanList[idx])
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}