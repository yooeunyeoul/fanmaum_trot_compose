package com.trotfan.trot.ui.home.ranking.history.component.cumulative

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme

@Composable
fun CumulativeRanking(
    navController: NavController? = null,
    starInfo: StarRanking? = null,
    historyViewModel: RankingHistoryViewModel = viewModel()
) {
    val lazyColumnState = rememberLazyListState()
    val starRankingDetail = historyViewModel.starRankingDetail.collectAsState().value

    Column {
        CustomTopAppBar(title = "임영웅 일간누적순위", icon = R.drawable.icon_back) {
            navController?.popBackStack()
        }

        LazyColumn(state = lazyColumnState) {
            item {
                CumulativeRankingHeader(title = "2022년 1월")
            }

            items(starRankingDetail.size) { index ->
                CumulativeRankingItem(cumulativeRanking = starRankingDetail[index])
            }
        }
    }
}

@Preview
@Composable
fun CumulativeRankingPreview() {
    FanwooriTheme {
        CumulativeRanking()
    }
}