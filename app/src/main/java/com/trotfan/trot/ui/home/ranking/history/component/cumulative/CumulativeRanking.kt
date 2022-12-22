package com.trotfan.trot.ui.home.ranking.history.component.cumulative

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.model.CumulativeRankingTest
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.theme.FanwooriTheme
import java.util.Collections

@Composable
fun CumulativeRanking(navController: NavController? = null) {
    val lazyColumnState = rememberLazyListState()
    val temp = mutableListOf<CumulativeRankingTest>()
    for (i in 0.rangeTo(30)) {
        temp.add(
            CumulativeRankingTest(
                day = i.plus(1),
                votes = i * 1050,
                score = i + 10,
                rank = i * 2
            )
        )
    }
    temp.sortByDescending { it.day }
    Column {
        CustomTopAppBar(title = "임영웅 일간누적순위", icon = R.drawable.icon_back) {
            navController?.popBackStack()
        }

        LazyColumn(state = lazyColumnState) {
            item {
                CumulativeRankingHeader(title = "2022년 1월")
            }

            items(temp.size) { index ->
                CumulativeRankingItem(cumulativeRanking = temp[index])
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