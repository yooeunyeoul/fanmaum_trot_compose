package com.trotfan.trot.ui.home.mypage.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.mypage.home.component.MyPageList
import com.trotfan.trot.ui.home.mypage.home.component.MyProfile
import com.trotfan.trot.ui.home.mypage.home.component.MyVotingTicket
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.Gray100

@Composable
fun MyPageHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    onNavigateClick: (HomeSections) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    BackHandler {
        onNavigateClick.invoke(HomeSections.Vote)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
            .verticalScroll(scrollState)
    ) {
        AppbarL(
            title = "마이페이지",
            icon = R.drawable.icon_setting,
            modifier = Modifier
                .background(Gray100)
                .padding(start = 16.dp, end = 16.dp),
            onIconClick = {
                navController?.navigate(Route.Setting.route)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        MyProfile(onClick = {
            navController?.navigate(Route.MyProfileModify.route)
        })

        Spacer(modifier = Modifier.height(16.dp))

        MyVotingTicket(onClick = {
            navController?.navigate(Route.MyVoteHistory.route)
        })

        Spacer(modifier = Modifier.height(24.dp))

        MyPageList()

        Spacer(modifier = Modifier.height(80.dp))

    }
}

@Preview
@Composable
fun MyPagePreview() {
    FanwooriTheme {
        MyPageHome(onItemClick = {}, onNavigateClick = {})
    }
}