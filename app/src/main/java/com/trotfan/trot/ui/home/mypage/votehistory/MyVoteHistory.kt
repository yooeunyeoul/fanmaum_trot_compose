package com.trotfan.trot.ui.home.mypage.votehistory

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.model.Ticket
import com.trotfan.trot.ui.components.chip.ChipTab
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyVoteHistory(
    navController: NavController? = null,
    viewModel: MyVoteHistoryViewModel = hiltViewModel(),
    onChargeClick: () -> Unit,
    purchaseHelper: PurchaseHelper?
) {
    val decimal = DecimalFormat("#,###")
    val historyItems: LazyPagingItems<Ticket>? =
        viewModel.userTicketHistory.value?.collectAsLazyPagingItems()
    val lazyState = rememberLazyListState()
    val tabs = listOf("전체", "미션보상", "구매", "사용", "소멸", "기타")
    var selectTab by remember {
        mutableStateOf(tabs[0])
    }
//    val unlimitedTicket by viewModel.unlimitedTicket.collectAsState()
//    val todayTicket by viewModel.todayTicket.collectAsState()
    val ticket by purchaseHelper?.tickets!!.collectAsState()
    var appBarColor by remember {
        mutableStateOf(Gray100)
    }
    var isEmpty by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBarColor)
    ) {
        AppbarMLeftIcon(
            title = "내 투표권 내역",
            icon = R.drawable.icon_back,
            modifier = Modifier.background(if (lazyState.firstVisibleItemIndex >= 1) Color.White else Color.Transparent),
            onIconClick = {
                navController?.popBackStack()
            })

        isEmpty = when (historyItems?.loadState?.refresh) {
            is LoadState.Error -> {
                selectTab == "전체"
            }
            else -> {
                false
            }
        }

        if (isEmpty) {
            appBarColor = Color.White
            EmptyHistory(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                onChargeClick = onChargeClick,
                navController = navController
            )
        } else {
            LazyColumn(state = lazyState) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        shape = RoundedCornerShape(24.dp),
                        backgroundColor = Color.White,
                        elevation = 1.dp
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
                                text = decimal.format(ticket.unlimited + ticket.today),
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
                                    horizontalAlignment = End
                                ) {
                                    Text(
                                        text = decimal.format(ticket.unlimited),
                                        style = FanwooriTypography.button1,
                                        color = Gray800
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = decimal.format(ticket.today),
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
                                            val filter: String? = when (selectTab) {
                                                "전체" -> null
                                                "미션보상" -> "rewarded"
                                                "구매" -> "purchased"
                                                "사용" -> "used"
                                                "소멸" -> "expired"
                                                else -> "etc"
                                            }
                                            viewModel.getUserTicketHistory(filter)
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

                historyItems?.let {
                    items(it.itemCount) { index ->
                        historyItems[index]?.let { item ->
                            HistoryItem(
                                item = item,
                                index,
                                if (index == 0) null else historyItems[index.minus(1)]
                            )
                        }
                    }
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
fun HistoryItem(item: Ticket, position: Int, beforeItem: Ticket?) {
    val decimal = DecimalFormat("#,###")
    val isPlus = item.quantity > 0
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val simpleDate = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    val date = dateFormat.parse(item.created_at)
    val result = date?.let { simpleDate.format(it) }

    Column {
        if (position == 0) {
            HistoryDateItem(date = result ?: "")
        } else {
            beforeItem?.created_at?.let {
                if (it != item.created_at) {
                    HistoryDateItem(date = result ?: "")
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
                    text = if (isPlus) "+${decimal.format(item.quantity)}" else decimal.format(
                        item.quantity
                    ),
                    style = FanwooriTypography.button1,
                    color = if (isPlus) SemanticNegative300 else Gray750,
                    modifier = Modifier.align(End)
                )

                Text(
                    text = item.filter,
                    style = FanwooriTypography.caption1,
                    color = Gray700,
                    modifier = Modifier.align(End)
                )
            }
        }
    }
}

@Composable
fun EmptyHistory(modifier: Modifier, navController: NavController?, onChargeClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "투표권 내역이 없어요",
            style = FanwooriTypography.subtitle1,
            color = Gray800,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "무료 충전소에서 미션을 수행하고\n" +
                    "투표권을 모아보세요!", style = FanwooriTypography.body5, color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .height(48.dp)
                .align(CenterHorizontally)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Secondary50
                )
                .border(1.dp, Secondary300, RoundedCornerShape(14.dp))
                .clickable {
                    navController?.popBackStack()
                    onChargeClick()
                }
        ) {
            Spacer(modifier = Modifier.width(22.dp))
            Image(
                painter = painterResource(id = R.drawable.icon_charge),
                contentDescription = null,
                modifier = Modifier.align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "충전 하러가기",
                style = FanwooriTypography.button1,
                color = Secondary800,
                modifier = Modifier.align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}

@Preview
@Composable
fun EmptyHistoryPreview() {
    FanwooriTheme {
        EmptyHistory(modifier = Modifier, onChargeClick = {}, navController = null)
    }
}

@Preview
@Composable
fun VoteHistoryItemPreview() {
}

@Preview
@Composable
fun MyVoteHistoryPreview() {
    FanwooriTheme {
        MyVoteHistory(purchaseHelper = null, onChargeClick = {})
    }
}