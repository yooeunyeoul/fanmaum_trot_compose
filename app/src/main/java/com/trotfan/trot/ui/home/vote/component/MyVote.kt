package com.trotfan.trot.ui.home.vote.component

import android.text.style.UnderlineSpan
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Composable
fun MyVote(modifier: Modifier = Modifier) {
    val textList = listOf("구매한 투표권", "친구초대 투표권", "소멸 예정 투표권")
    var isHide by remember {
        mutableStateOf(true)
    }
    Column(
        modifier
            .fillMaxWidth()
            .background(color = Gray100, shape = RoundedCornerShape(16.dp))
            .padding(start = 24.dp, end = 24.dp, top = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    isHide = !isHide
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
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

            Text(
                text = "129,239,000",
                color = Primary900,
                style = FanwooriTypography.button1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.icon_arrow),
                contentDescription = null,
                modifier = Modifier.rotate(90f)
            )

        }

        AnimatedVisibility(visible = !isHide) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(22.dp))

                repeat(3) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = textList[it],
                            color = Gray900,
                            style = FanwooriTypography.body3,
                            fontSize = 17.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "200",
                            color = Gray900,
                            style = FanwooriTypography.button1,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Charging()
            }
        }


    }
}

@Composable
fun Charging() {
    Text(
        text = "충전하기",
        color = Primary800,
        style = FanwooriTypography.button1,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        textDecoration = TextDecoration.Underline
    )

}

@Preview
@Composable
fun PreviewMyVote() {
    MyVote()
}