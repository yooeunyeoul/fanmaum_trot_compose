package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Preview
@Composable
fun SampleMyPageList() {

    var height by mutableStateOf(48.dp)

    val textList =
        listOf<String>("설정", "계정정보", "앱 알림 설정", "친구 초대 / 추천인 입력", "앱 정보", "높이를 변경하려면 터치 해주세요.")

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = 6) { index ->
                if (index == 0) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(color = Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = null,
                            tint = Gray700,
                            modifier = Modifier.padding(start = 20.2.dp)
                        )
                        Spacer(modifier = Modifier.width(22.2.dp))
                        Text(
                            text = "현재 높이 ${height} 입니다.",
                            color = Color.Red,
                            style = FanwooriTypography.body2,
                            fontSize = 15.sp
                        )

                    }
                } else {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(height)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp)
                                .height(height)
                                .clickable {
                                    if (index == 5) {
                                        when (height) {
                                            56.dp -> {
                                                height = 64.dp
                                            }
                                            64.dp -> {
                                                height = 48.dp
                                            }
                                            48.dp -> {
                                                height = 56.dp
                                            }
                                        }
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = textList[index],
                                color = Gray750,
                                style = FanwooriTypography.body3,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Medium,
                                fontSize = 17.sp
                            )
                            if (index == 4) {
                                Text(
                                    text = "현재 버전 1.1.2",
                                    color = Primary500,
                                    style = FanwooriTypography.caption1,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(end = 14.05.dp)
                                )

                            }
                            Icon(
                                painter = painterResource(id = R.drawable.icon_arrow),
                                contentDescription = null,
                                tint = Gray700
                            )
                        }
                        Divider(
                            Modifier
//                            .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp)
                                .background(color = Gray100)
//                            .padding(start = 24.dp, end = 24.dp)
                                .align(Alignment.BottomCenter),
                            thickness = 1.dp
                        )

                    }

                }
            }
        }


    }


}