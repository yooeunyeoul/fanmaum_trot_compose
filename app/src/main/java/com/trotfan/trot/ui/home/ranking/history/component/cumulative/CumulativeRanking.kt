package com.trotfan.trot.ui.home.ranking.history.component.cumulative

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
    starId: Int? = null,
    starName: String? = null,
    date: String? = null,
    historyViewModel: CumulativeViewModel = hiltViewModel()
) {
    val lazyColumnState = rememberLazyListState()
    val starRankingDetail = historyViewModel.starRankingDetail.collectAsState().value
    val year = date?.split("-")?.get(0)
    val month = date?.split("-")?.get(1)
    LaunchedEffect(starId) {
        starId?.let {
            historyViewModel.getStarRankingDetail(it, year ?: "2022", month ?: "11")
        }
    }

    Column {
        CustomTopAppBar(title = "$starName 일간누적순위", icon = R.drawable.icon_back) {
            navController?.popBackStack()
        }

        LazyColumn(state = lazyColumnState) {
            item {
                CumulativeRankingHeader(title = "${year}년 ${month}월")
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