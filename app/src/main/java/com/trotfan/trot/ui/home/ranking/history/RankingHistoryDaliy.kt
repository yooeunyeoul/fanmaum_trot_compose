package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.ranking.history.component.daily.EmptyDailyRankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingHistoryDailyHeader
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingStarDailyItem

@Composable
fun RankingHistoryDaily(
    emptyState: Boolean = false,
    onCalenderClick: () -> Unit
) {
    val stars = listOf(
        VoteMainStar(0, null, "최영화", 1, 13000),
        VoteMainStar(1, null, "이민혁", 2, 12000),
        VoteMainStar(2, null, "임형규", 3, 11000),
        VoteMainStar(3, null, "유은열", 4, 9000),
        VoteMainStar(4, null, "김준영", 5, 7000),
        VoteMainStar(5, null, "이소진", 6, 555),
        VoteMainStar(6, null, "박수빈", 7, 10),
    )

    if (emptyState) {
        EmptyDailyRankingHistory()
    } else {

        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                RankingHistoryDailyHeader(onCalenderClick = {
                    onCalenderClick()
                })
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(stars.size) { idx ->
                RankingStarDailyItem(star = stars[idx])
            }
        }
    }
}