package com.trotfan.trot.ui.home.mypage.modify.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.trotfan.trot.datastore.UserInfoDataStore
import com.trotfan.trot.datastore.UserInfoManager.Companion.USER_TOTAL_USED_VOTE
import com.trotfan.trot.ui.home.mypage.home.MyPageViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun ProfileInfo(
    viewModel: MyPageViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var userTotalUsedVote by remember {
        mutableStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    coroutineScope.launch {
        context.UserInfoDataStore.data.collect {
            userTotalUsedVote = it[USER_TOTAL_USED_VOTE] ?: 0
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Gray50
            )
    ) {
        val decimal = DecimalFormat("#,###")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "내 스타",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
            Text(
                text = viewModel.starName.value,
                style = FanwooriTypography.button1,
                color = Gray900,
                modifier = Modifier.align(CenterVertically)
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .background(
                    Gray100
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "누적 투표 수",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
            Text(
                text = decimal.format(userTotalUsedVote),
                style = FanwooriTypography.button1,
                color = Gray900,
                modifier = Modifier.align(CenterVertically)
            )
        }
    }
}