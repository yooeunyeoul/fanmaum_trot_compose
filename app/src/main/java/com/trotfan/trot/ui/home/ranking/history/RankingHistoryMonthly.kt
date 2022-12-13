package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.ranking.history.component.monthly.EmptyRankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingHistoryBanner
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingHistoryMonthlyHeader
import com.trotfan.trot.ui.home.ranking.history.component.monthly.RankingStarItem
import com.trotfan.trot.ui.theme.FanwooriTheme

@Composable
fun RankingHistoryMonthly(
    emptyState: Boolean = false
) {
    Column {
        if (emptyState) {
            EmptyRankingHistory()
        } else {
            Spacer(modifier = Modifier.height(24.dp))

            RankingHistoryMonthlyHeader()

            Spacer(modifier = Modifier.height(24.dp))

            val stars = listOf(
                VoteMainStar(0, null, "최영화", 1, 13000),
                VoteMainStar(1, null, "이민혁", 2, 12000),
                VoteMainStar(2, null, "임형규", 3, 11000),
                VoteMainStar(3, null, "유은열", 4, 9000),
                VoteMainStar(4, null, "김준영", 5, 7000),
                VoteMainStar(5, null, "이소진", 6, 555),
                VoteMainStar(6, null, "박수빈", 7, 10),
            )

            LazyColumn {
                item {
                    RankingHistoryBanner()
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(stars.size) { idx ->
                    RankingStarItem(star = stars[idx])
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun RankingHistoryMonthlyPreview() {
    FanwooriTheme {
        RankingHistoryMonthly()
    }
}