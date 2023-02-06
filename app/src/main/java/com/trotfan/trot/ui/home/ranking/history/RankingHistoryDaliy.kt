package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.trotfan.trot.ui.home.ranking.history.component.daily.EmptyDailyRankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingHistoryDailyHeader
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingStarDailyItem
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import kotlinx.coroutines.launch

@Composable
fun RankingHistoryDaily(
    emptyState: Boolean = false,
    onCalenderClick: () -> Unit,
    navController: NavController? = null,
    onVotingClick: () -> Unit?,
    viewModel: RankingHistoryViewModel = viewModel()
) {
    val manList = viewModel.dailyManList.collectAsState().value
    val womanList = viewModel.dailyWomanList.collectAsState().value
    val favoriteStarGender by viewModel.dailyGender.collectAsState()

    var genderIndex by remember {
        mutableStateOf(if (favoriteStarGender == Gender.MEN) 0 else 1)
    }
    val coroutineScope = rememberCoroutineScope()

    Column {
        if (emptyState) {
            EmptyDailyRankingHistory(navController = navController, onVotingClick = onVotingClick)
        } else {
            Spacer(modifier = Modifier.height(24.dp))
            RankingHistoryDailyHeader(gender = favoriteStarGender, onCalenderClick = {
                onCalenderClick()
            }, onGenderClick = {
                coroutineScope.launch {
                    viewModel.dailyGender.emit(if (it == 0) Gender.MEN else Gender.WOMEN)
                    genderIndex = it
                }
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