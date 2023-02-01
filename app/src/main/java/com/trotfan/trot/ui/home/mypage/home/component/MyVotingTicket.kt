package com.trotfan.trot.ui.home.mypage.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.mypage.home.MyPageViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary900
import com.trotfan.trot.ui.utils.clickable
import java.text.DecimalFormat

@Composable
fun MyVotingTicket(
    onClick: () -> Unit,
    viewModel: MyPageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    purchaseHelper: PurchaseHelper?
) {
    val decimal = DecimalFormat("#,###")
    val ticket by purchaseHelper?.tickets!!.collectAsState()
//    val unlimitedTicket by viewModel.unlimitedTicket.collectAsState()
//    val todayTicket by viewModel.todayTicket.collectAsState()

    Surface(
        color = Color.White,
        elevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
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
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = decimal.format(ticket.unlimited + ticket.today),
                style = FanwooriTypography.h2,
                color = Gray800
            )

            Spacer(modifier = Modifier.height(6.dp))

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
        MyVotingTicket({

        }, purchaseHelper = null)
    }
}