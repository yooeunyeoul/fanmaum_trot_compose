package com.trotfan.trot.ui.home.mypage.modify.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.*
import java.text.DecimalFormat

@Composable
fun ProfileInfo() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Gray50
            )
    ) {
        val decimal = DecimalFormat("#,###")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "내 스타",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier.weight(1f).align(CenterVertically)
            )
            Text(
                text = "임영웅",
                style = FanwooriTypography.button1,
                color = Gray900,
                modifier = Modifier.align(CenterVertically)
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .background(
                    Gray100
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "누적 투표수",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
            Text(
                text = decimal.format(250000),
                style = FanwooriTypography.button1,
                color = Gray900,
                modifier = Modifier.align(CenterVertically)
            )
        }
    }
}