package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.theme.FanwooriTheme

@Composable
fun MyPageHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            AppbarL(
                title = "마이페이지",
                icon = R.drawable.icon_setting,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            MyProfile()
        }
    }
}

@Preview
@Composable
fun MyPagePreview() {
    FanwooriTheme {
        MyPageHome(onItemClick = {})
    }
}