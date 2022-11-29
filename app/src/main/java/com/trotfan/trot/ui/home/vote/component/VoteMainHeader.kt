package com.trotfan.trot.ui.home.vote.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.ui.theme.*

@Composable
fun HeaderVoteState(myStarName: String, dayRank: Int, monthRank: Int, month: Int) {
    Column(Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(23.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = myStarName,
                style = FanwooriTypography.subtitle1,
                fontWeight = FontWeight.SemiBold,
                color = Gray900,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "에게",
                style = FanwooriTypography.body5,
                color = Gray700,
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "투표하기",
                style = FanwooriTypography.h1,
                fontWeight = FontWeight.SemiBold,
                color = Secondary600,
                fontSize = 28.sp,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                painter = painterResource(id = com.trotfan.trot.R.drawable.icon_back),
                contentDescription = null,
                modifier = Modifier.rotate(180f),
                tint = Secondary600
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "일일순위",
                    style = FanwooriTypography.body5,
                    color = Gray900,
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (dayRank == -1) "-" else "${dayRank}위",
                    style = FanwooriTypography.h2,
                    color = Gray800,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }

            Divider(
                color = Gray300, modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(top = 20.dp, bottom = 20.dp)
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${month}월 현재순위",
                    style = FanwooriTypography.body5,
                    color = Gray900,
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (monthRank == -1) "-" else "${monthRank}위",
                    style = FanwooriTypography.h2,
                    color = Gray800,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}


@Composable
fun HeaderEndState(myStarName: String) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = myStarName,
                style = FanwooriTypography.h3,
                color = Gray800,
            )
            Text(
                text = "님의",
                style = FanwooriTypography.subtitle1,
                color = Gray700,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "순위 공개를 기다려주세요!",
            style = FanwooriTypography.subtitle1,
            color = Gray700,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))
    }

}