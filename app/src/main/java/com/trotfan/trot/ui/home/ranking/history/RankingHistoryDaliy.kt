package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.home.ranking.history.component.daily.RankingHistoryDailyHeader

@Composable
fun RankingHistoryDaily() {
    Spacer(modifier = Modifier.height(24.dp))

    RankingHistoryDailyHeader()
}