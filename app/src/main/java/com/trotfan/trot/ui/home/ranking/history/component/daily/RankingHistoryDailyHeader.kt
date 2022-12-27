package com.trotfan.trot.ui.home.ranking.history.component.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.ranking.history.component.RankingHistoryGenderTab
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Composable
fun RankingHistoryDailyHeader(
    onCalenderClick: () -> Unit,
    onGenderClick: (Int) -> Unit,
    rankingHistoryViewModel: RankingHistoryViewModel = viewModel()
) {

    val year = rankingHistoryViewModel.dailyYear.collectAsState()
    val month = rankingHistoryViewModel.dailyMonth.collectAsState()
    val day = rankingHistoryViewModel.dailyDay.collectAsState()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Gray100)
                .clickable {
                    onCalenderClick()
                }
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_icon_calendar),
                    contentDescription = null,
                    tint = Gray750
                )

                Spacer(modifier = Modifier.width(4.dp))

                val tempMonth: String = if (month.value < 10) {
                    "0${month.value}"
                } else {
                    "${month.value}"
                }

                var tempDay = ""
                tempDay = if (day.value < 10) {
                    "0${day.value}"
                } else {
                    "${day.value}"
                }

                Text(
                    text = "${year.value}.${tempMonth}.${tempDay}",
                    color = Gray900,
                    style = FanwooriTypography.body3
                )

            }

            IconButton(
                modifier = Modifier
                    .align(CenterVertically)
                    .clip(CircleShape)
                    .background(Gray900)
                    .rotate(90f)
                    .size(24.dp),
                onClick = {
                    onCalenderClick()
                }) {

                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        RankingHistoryGenderTab(onGenderClick = {
            onGenderClick(it)
        })
    }
}

@Preview
@Composable
fun RankingHistoryHeaderPreview() {
    FanwooriTheme {
        RankingHistoryDailyHeader(onCalenderClick = {

        }, onGenderClick = {})
    }
}
