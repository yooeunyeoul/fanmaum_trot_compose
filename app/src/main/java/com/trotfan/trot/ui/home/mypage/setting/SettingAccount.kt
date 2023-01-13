package com.trotfan.trot.ui.home.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*

@Composable
fun SettingAccount() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AppbarMLeftIcon(title = "계정 정보", icon = R.drawable.icon_back)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "연동계정",
                style = FanwooriTypography.body2,
                color = Gray650,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(Gray50)
            ) {
                Text(
                    text = "이름",
                    style = FanwooriTypography.body3,
                    color = Gray900,
                    modifier = Modifier
                        .align(
                            Alignment.TopStart
                        )
                        .padding(start = 24.dp, top = 24.dp)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kakao_symbol),
                        contentDescription = null,
                        tint = Gray900,
                        modifier = Modifier.align(CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Kakao", style = FanwooriTypography.body2, color = Gray900,
                        modifier = Modifier.align(CenterVertically)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(BottomEnd)
                        .padding(top = 76.dp, end = 24.dp, bottom = 24.dp)
                ) {
                    Text(text = "연동일", style = FanwooriTypography.caption1, color = Gray600)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "2022.12.12", style = FanwooriTypography.body2, color = Gray600)
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "회원탈퇴",
                    style = FanwooriTypography.button1,
                    color = Gray500,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier
                        .width(62.dp)
                        .height(1.dp)
                        .background(Gray500)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingAccountPreview() {
    SettingAccount()
}