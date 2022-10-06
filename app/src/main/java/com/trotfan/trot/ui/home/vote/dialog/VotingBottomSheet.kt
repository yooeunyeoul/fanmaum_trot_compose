package com.trotfan.trot.ui.home.vote.dialog

import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import java.text.DecimalFormat

@Composable
fun VotingBottomSheet() {
    val context = LocalContext.current
    val decimal = DecimalFormat("#,###")
    val myVoteCnt by remember { mutableStateOf(14000000) }
    var voteCnt by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_close),
                contentDescription = null,
                tint = Gray700,
                modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 16.dp)
            )
            Text(
                text = "투표",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "내 투표권",
            style = FanwooriTypography.body2,
            color = Gray700,
            modifier = Modifier.padding(start = 32.dp, top = 32.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = decimal.format(myVoteCnt),
                style = FanwooriTypography.subtitle2,
                color = Gray800,
                modifier = Modifier
                    .padding(start = 32.dp)
                    .weight(1f)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp, end = 34.dp)
            ) {
                Text(
                    text = "충전하기",
                    style = FanwooriTypography.button1,
                    color = Primary800,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Box(
                    modifier = Modifier
                        .background(Primary800)
                        .width(62.dp)
                        .height(1.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 32.dp, end = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Primary50
                )
        ) {
            Text(
                text = "오늘 소멸 예정 투표권",
                style = FanwooriTypography.body2,
                color = Primary800,
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 16.dp)
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .align(CenterVertically),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_vote_iconcolored),
                    contentDescription = null
                )

                Text(
                    text = "400",
                    style = FanwooriTypography.subtitle3,
                    color = Primary800,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }

        Text(
            text = "스타이름 님에게",
            style = FanwooriTypography.subtitle1,
            color = Gray800,
            modifier = Modifier
                .padding(
                    top = 80.dp
                )
                .align(CenterHorizontally)
        )

        TextField(
            value = voteCnt,
            onValueChange = { cnt ->
                voteCnt = if (cnt.isBlank().not()) {
                    var decimalCnt = cnt.replace(",", "")
                    if (decimalCnt.toLong() >= myVoteCnt) {
                        decimalCnt = myVoteCnt.toString()
                    }
                    decimal.format(decimalCnt.toLong())
                } else {
                    ""
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "얼마나 투표할까요?",
                    style = FanwooriTypography.h1,
                    textAlign = TextAlign.Center
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Gray50,
                placeholderColor = Gray400,
                textColor = Gray900,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            singleLine = true,
            textStyle = FanwooriTypography.h1.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .align(CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 16.dp)
                .background(Color.Transparent)
        )
    }
}

@Preview
@Composable
fun VotingBottomSheetPreview() {
    FanwooriTheme {
        VotingBottomSheet()
    }
}