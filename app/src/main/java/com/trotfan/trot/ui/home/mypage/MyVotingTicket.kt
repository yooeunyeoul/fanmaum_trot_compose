package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary900
import java.text.DecimalFormat

@Composable
fun MyVotingTicket() {
    val decimal = DecimalFormat("#,###")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row {
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
            Text(text = decimal.format(14000), style = FanwooriTypography.h2, color = Gray800)

            Row(modifier = Modifier.align(End)) {
                Text(text = "이용내역", style = FanwooriTypography.button1, color = Primary900)
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = Primary900,
                    modifier = Modifier.align(CenterVertically)
                )
            }
        }
    }
}

@Preview
@Composable
fun MyVotingTicketPreview() {
    FanwooriTheme {
        MyVotingTicket()
    }
}