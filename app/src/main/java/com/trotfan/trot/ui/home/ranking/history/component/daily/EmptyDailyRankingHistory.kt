package com.trotfan.trot.ui.home.ranking.history.component.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnOutlineSecondaryLeftIcon
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary500

@Composable
fun EmptyDailyRankingHistory(
    navController: NavController?,
    onVotingClick: () -> Unit?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.ic_ranking_nohistory),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "팬마음 첫 투표 진행 중!",
            color = Gray800,
            style = FanwooriTypography.subtitle1,
            modifier = Modifier.align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "일일 순위는 오늘 투표를 반영해", color = Gray700, style = FanwooriTypography.body5,
            modifier = Modifier.align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "다음 날부터 공개", color = Primary500, style = FanwooriTypography.button1)
            Text(text = "됩니다.", color = Gray700, style = FanwooriTypography.body5)
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "지금 바로 내 스타에게 투표해보세요!", color = Gray700, style = FanwooriTypography.body5,
            modifier = Modifier.align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        BtnOutlineSecondaryLeftIcon(
            text = "투표하러 가기",
            onClick = {
                navController?.popBackStack()
                onVotingClick()
            },
            icon = R.drawable.icon_vote,
            isCircle = false
        )
    }
}