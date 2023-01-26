package com.trotfan.trot.ui.home.mypage.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray750
import com.trotfan.trot.ui.utils.clickableSingle

@Composable
fun MyPageList() {
    Surface(
        color = Color.White,
        elevation = 1.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            MyPageListComponent(text = "공지사항", url = "")
            Line()
            MyPageListComponent(text = "이벤트", url = "")
            Line()
            MyPageListComponent(text = "이용가이드", url = "")
            Line()
            MyPageListComponent(text = "자주 묻는 질문", url = "")
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(start = 24.dp, end = 24.dp)
            .background(Gray100)
    )
}

@Composable
fun MyPageListComponent(text: String, url: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickableSingle {

            }
    ) {
        Text(
            text = text,
            style = FanwooriTypography.body3,
            color = Gray750,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 24.dp)
                .weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray750,
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 24.dp)
        )
    }
}

@Preview
@Composable
fun MyPageListPreview() {
    FanwooriTheme {
        MyPageList()
    }
}