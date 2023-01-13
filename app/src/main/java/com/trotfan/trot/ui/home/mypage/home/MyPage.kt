package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.Gray100

@Composable
fun MyPageHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Gray100)
    ) {
        AppbarL(
            title = "마이페이지",
            icon = R.drawable.icon_setting,
            modifier = Modifier
                .background(Gray100)
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        MyProfile()

        Spacer(modifier = Modifier.height(16.dp))

        MyVotingTicket()

        Spacer(modifier = Modifier.height(24.dp))

        MyPageList()

    }
}

@Preview
@Composable
fun MyPagePreview() {
    FanwooriTheme {
        MyPageHome(onItemClick = {})
    }
}