package com.trotfan.trot.ui.home.ranking.history.component.monthly

import android.util.Log
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
import kotlinx.coroutines.launch

@Composable
fun RankingHistoryMonthlyHeader(
    onCalenderClick: (String) -> Unit,
    onCalenderBefore: () -> Unit,
    onCalenderAfter: () -> Unit,
    onGenderClick: (Int) -> Unit,
    rankingHistoryViewModel: RankingHistoryViewModel = viewModel()
) {
    val year = rankingHistoryViewModel.monthlyYear.collectAsState()
    val month = rankingHistoryViewModel.monthlyMonth.collectAsState()
    val isEnded = rankingHistoryViewModel.isEnded.collectAsState().value
    val isStared = rankingHistoryViewModel.isStared.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Gray100)
                .clickable { onCalenderClick("${year.value}/${month.value}") }
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(
                enabled = isStared.not(),
                modifier = Modifier
                    .align(CenterVertically)
                    .clip(CircleShape)
                    .background(if (isStared) Gray300 else Gray900)
                    .size(24.dp),
                onClick = {
                    onCalenderBefore()
                }) {

                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.rotate(180f)
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
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

                Text(
                    text = "${year.value}.${tempMonth}",
                    color = Gray900,
                    style = FanwooriTypography.body3
                )

            }

            IconButton(
                enabled = isEnded.not(),
                modifier = Modifier
                    .align(CenterVertically)
                    .clip(CircleShape)
                    .background(if (isEnded) Gray300 else Gray900)
                    .size(24.dp),
                onClick = {
                    onCalenderAfter()
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

        RankingHistoryGenderTab(onGenderClick = onGenderClick)
    }
}

@Preview
@Composable
fun RankingHistoryHeaderPreview() {
    FanwooriTheme {
        RankingHistoryMonthlyHeader(
            onCalenderClick = {

            },
            onGenderClick = {

            },
            onCalenderAfter = {},
            onCalenderBefore = {}
        )
    }
}
