package com.trotfan.trot.ui.home.mypage.votehistory

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.model.VoteHistoryItem
import com.trotfan.trot.ui.components.chip.ChipTab
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import java.text.DecimalFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyVoteHistory(
    navController: NavController? = null,
    viewModel: MyVoteHistoryViewModel = hiltViewModel()
) {
    val decimal = DecimalFormat("#,###")
    val historyItems = listOf<VoteHistoryItem>(
        VoteHistoryItem("2023.01.17", 0, "일일미션 완료", 500),
        VoteHistoryItem("2023.01.17", 1, "일일투표 - 임영웅", 500),
        VoteHistoryItem("2023.01.17", 0, "투표권 구매", 5000),
        VoteHistoryItem("2023.01.15", 0, "일일미션 완료", 500),
        VoteHistoryItem("2023.01.13", 1, "일일투표 - 영탁", 1500),
        VoteHistoryItem("2023.01.13", 0, "일일미션 완료", 500),
        VoteHistoryItem("2023.01.11", 1, "투표권 소멸", 500),
        VoteHistoryItem("2023.01.10", 0, "일일미션 완료", 500),
        VoteHistoryItem("2023.01.09", 1, "일일투표 - 영탁", 1500),
        VoteHistoryItem("2023.01.09", 0, "일일미션 완료", 500),
        VoteHistoryItem("2023.01.02", 1, "투표권 소멸", 500),
        VoteHistoryItem("2023.01.02", 0, "일일미션 완료", 500),
    )
    val lazyState = rememberLazyListState()
    val tabs = listOf("전체", "구매", "사용", "소멸")
    var selectTab by remember {
        mutableStateOf(tabs[0])
    }
    val unlimitedTicket by viewModel.unlimitedTicket.collectAsState()
    val todayTicket by viewModel.todayTicket.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        AppbarMLeftIcon(
            title = "내 투표권 내역",
            icon = R.drawable.icon_back,
            modifier = Modifier.background(if (lazyState.firstVisibleItemIndex >= 1) Color.White else Gray100),
            onIconClick = {
                navController?.popBackStack()
            })

        LazyColumn(state = lazyState) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .background(Color.White),
                    elevation = 4.dp
                ) {
                    Column {
                        Row(modifier = Modifier.padding(top = 26.dp, start = 24.dp)) {
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
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = decimal.format(unlimitedTicket + todayTicket),
                            style = FanwooriTypography.h1,
                            color = Gray900,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(56.dp)
                                    .background(Gray100)
                                    .align(CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = "유효기한 무제한",
                                    style = FanwooriTypography.body3,
                                    color = Gray700
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "오늘 소멸 예정",
                                    style = FanwooriTypography.body3,
                                    color = Gray700
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = decimal.format(unlimitedTicket),
                                    style = FanwooriTypography.button1,
                                    color = Gray800
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = decimal.format(todayTicket),
                                    style = FanwooriTypography.button1,
                                    color = Gray800
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    LazyRow {
                        item {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                        items(tabs.size) { index ->
                            if (index != 0) Spacer(modifier = Modifier.width(6.dp))
                            ChipTab(
                                modifier = Modifier,
                                text = tabs[index],
                                isOn = selectTab == tabs[index],
                                onClick = {
                                    if (selectTab != tabs[index]) {
                                        selectTab = tabs[index]
                                    }
                                })
                        }
                        item {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_info),
                        contentDescription = null,
                        tint = Gray700,
                        modifier = Modifier
                            .align(CenterVertically)
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "최근 6개월 내역만 확인할 수 있습니다.",
                        style = FanwooriTypography.caption2,
                        color = Gray700,
                        modifier = Modifier.align(CenterVertically)
                    )
                }
            }

            items(historyItems.size) { index ->
                HistoryItem(
                    item = historyItems[index],
                    index,
                    if (index == 0) null else historyItems[index.minus(1)]
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
fun HistoryDateItem(date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 32.dp, start = 24.dp, bottom = 8.dp)
    ) {
        Text(text = date, style = FanwooriTypography.caption1, color = Gray700)
    }
}

@Composable
fun HistoryItem(item: VoteHistoryItem, position: Int, beforeItem: VoteHistoryItem?) {
    val decimal = DecimalFormat("#,###")
    val isPlus = item.state == 0

    Column {
        if (position == 0) {
            HistoryDateItem(date = item.date)
        } else {
            beforeItem?.date?.let {
                if (it != item.date) {
                    HistoryDateItem(date = item.date)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(if (isPlus) SemanticNegative300 else SemanticPositive300)
                    .align(CenterVertically)
            ) {
                Icon(
                    painter = if (isPlus) painterResource(id = R.drawable.icon_add) else painterResource(
                        id = R.drawable.icon_minus
                    ), contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Center)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.title,
                color = Gray800,
                style = FanwooriTypography.button1,
                maxLines = 2,
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            Column(
                modifier = Modifier
                    .align(CenterVertically)
            ) {
                Text(
                    text = if (isPlus) "+${decimal.format(item.cnt)}" else "-${decimal.format(item.cnt)}",
                    style = FanwooriTypography.button1,
                    color = if (isPlus) SemanticNegative300 else Gray750,
                    modifier = Modifier.align(End)
                )

                Text(
                    text = if (isPlus) "보상" else "사용",
                    style = FanwooriTypography.caption1,
                    color = Gray700,
                    modifier = Modifier.align(End)
                )
            }
        }
    }
}

@Preview
@Composable
fun VoteHistoryItemPreview() {
    FanwooriTheme {
        HistoryItem(
            item =
            VoteHistoryItem("2023.01.17", 1, "일일투표 - 임영웅", 500), position = 0, beforeItem = null
        )
    }
}

@Preview
@Composable
fun MyVoteHistoryPreview() {
    FanwooriTheme {
        MyVoteHistory()
    }
}