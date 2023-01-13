package com.trotfan.trot.ui.home.mypage.votehistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import java.text.DecimalFormat

@Composable
fun MyVoteHistory() {
    val decimal = DecimalFormat("#,###")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        AppbarMLeftIcon(title = "내 투표권 내역", icon = R.drawable.icon_back)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(Color.White),
            elevation = 4.dp
        ) {
            Column {
                Row(modifier = Modifier.padding(top = 26.dp, start = 24.dp)) {
                    Text(
                        text = "내 투표권",
                        color = Primary900,
                        style = FanwooriTypography.body3,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Image(
                        painter = painterResource(id = R.drawable.icon_vote),
                        contentDescription = null,
                        modifier = Modifier.weight(1f, fill = true),
                        alignment = Alignment.CenterStart
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = decimal.format(800400),
                    style = FanwooriTypography.h1,
                    color = Gray900,
                    modifier = Modifier.padding(start = 24.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(56.dp)
                            .background(Gray100)
                            .align(CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = "유효기한 무제한", style = FanwooriTypography.body3, color = Gray700)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "오늘 소멸 예정", style = FanwooriTypography.body3, color = Gray700)
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = decimal.format(1000),
                            style = FanwooriTypography.button1,
                            color = Gray800
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = decimal.format(400),
                            style = FanwooriTypography.button1,
                            color = Gray800
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
fun MyVoteHistoryPreview() {
    FanwooriTheme {
        MyVoteHistory()
    }
}