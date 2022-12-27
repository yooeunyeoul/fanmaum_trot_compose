package com.trotfan.trot.ui.home.ranking.history.component.cumulative

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.model.StarRankingDetail
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma

@Composable
fun CumulativeRankingItem(cumulativeRanking: StarRankingDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(modifier = Modifier.height(55.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${cumulativeRanking.day}일",
                color = Gray700,
                style = FanwooriTypography.body5,
                modifier = Modifier
                    .width(48.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${NumberComma.decimalFormat.format(cumulativeRanking.votes)}표",
                color = Gray800,
                style = FanwooriTypography.body3,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${cumulativeRanking.point}점",
                color = Gray800,
                style = FanwooriTypography.body3,
                modifier = Modifier
                    .width(56.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${cumulativeRanking.rank}위",
                color = Primary500,
                style = FanwooriTypography.button1,
                modifier = Modifier
                    .width(56.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Primary100)
        )
    }
}

@Preview
@Composable
fun CumulativeRankingItemPreview() {
    FanwooriTheme {
        CumulativeRankingItem(
            StarRankingDetail(
                day = "30",
                votes = 300000,
                point = 50,
                rank = 30
            )
        )
    }
}