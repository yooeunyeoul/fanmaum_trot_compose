package com.trotfan.trot.ui.home.ranking.history.component.cumulative

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.*

@Composable
fun CumulativeRankingHeader(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            color = Gray900,
            style = FanwooriTypography.h1,
            modifier = Modifier.padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "한 달 동안의 순위내역을 확인할 수 있어요.",
            color = Gray700,
            style = FanwooriTypography.body5,
            modifier = Modifier.padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Primary50)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "일자",
                color = Primary700,
                style = FanwooriTypography.body2,
                modifier = Modifier
                    .width(48.dp)
                    .align(CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "투표수",
                color = Primary700,
                style = FanwooriTypography.body2,
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "일일점수",
                color = Primary700,
                style = FanwooriTypography.body2,
                modifier = Modifier
                    .width(56.dp)
                    .align(CenterVertically),
                textAlign = TextAlign.Center
            )

            Text(
                text = "순위",
                color = Primary700,
                style = FanwooriTypography.body2,
                modifier = Modifier
                    .width(56.dp)
                    .align(CenterVertically),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun CumulativeRankingHeaderPreview() {
    FanwooriTheme {
        CumulativeRankingHeader("2022년 1월")
    }
}