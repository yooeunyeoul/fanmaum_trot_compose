package com.trotfan.trot.ui.home.charge

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.android.billingclient.api.BillingClient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.*
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.list.StoreItem
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.home.BottomNavHeight
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.charge.component.MyVote
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.charge.viewmodel.MissionRewardState
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.composableActivityViewModel
import com.trotfan.trot.ui.utils.textBrush
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChargeHome(
    onItemClick: (Long) -> Unit,
    navController: NavController,
    onNavigateClick: (HomeSections) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChargeHomeViewModel = composableActivityViewModel(key = "ChargeHomeViewModel"),
    purchaseHelper: PurchaseHelper
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("무료충전소", "스토어")

    BackHandler {
        onNavigateClick.invoke(HomeSections.Vote)
    }

    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavHeight)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppbarL(title = "충전", modifier = Modifier.padding(start = 16.dp))

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(3.dp)
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(Primary300)
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { tabIndex, tab ->
                    androidx.compose.material3.Tab(
                        selected = pagerState.currentPage == tabIndex,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(tabIndex)
                            }
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White,
                        text = {
                            Text(
                                text = tab,
                                color = if (tabIndex == pagerState.currentPage) Primary900 else Gray700,
                                style = if (tabIndex == pagerState.currentPage) FanwooriTypography.button1 else FanwooriTypography.body3
                            )
                        },
                        modifier = Modifier.background(Color.White)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray200)
            )

            HorizontalPager(count = tabs.size, state = pagerState) { page ->
                when (page) {
                    0 -> FreeChargeView(navController, viewModel = viewModel)
                    else -> StoreView(purchaseHelper = purchaseHelper, viewModel = viewModel)
                }
            }

        }

    }
}

@Composable
fun FreeChargeView(
    navController: NavController,
    viewModel: ChargeHomeViewModel
) {
    val scrollState = rememberScrollState()
    val missionState by viewModel.missionState.collectAsState()
    val adCount by viewModel.videoCount.collectAsState()
    val rouletteCount by viewModel.rouletteCount.collectAsState()
    val rewardedState by viewModel.rewardedState.collectAsState()
    val attendanceState by viewModel.attendanceState.collectAsState()
    val simpleDateFormat = SimpleDateFormat("dd")
    val dateString = simpleDateFormat.format(Date()).toString()
    val localDate by viewModel.lastApiTime.collectAsState()

    LaunchedEffect(key1 = dateString, block = {
        if (dateString != localDate) {
            viewModel.getMissions()
        }

    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray50)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = Color.White,
                elevation = 1.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "일일 미션 완료하고",
                        style = FanwooriTypography.subtitle2,
                        color = Gray800,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.align(CenterHorizontally)) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_vote),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "3,200 투표권",
                            style = FanwooriTypography.subtitle2,
                            modifier = Modifier.textBrush(
                                gradient04
                            )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "받으세요!",
                            style = FanwooriTypography.subtitle2,
                            color = Gray800
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(start = 34.dp, end = 34.dp)
                            .border(
                                width = if (rewardedState == MissionRewardState.Incomplete) 2.dp else 0.dp,
                                brush = gradient04,
                                shape = RoundedCornerShape(26.dp)
                            )
                            .clip(RoundedCornerShape(26.dp))
                            .background(
                                when (rewardedState) {
                                    MissionRewardState.Incomplete -> Brush.linearGradient(
                                        0.0f to Color(
                                            0xFFFFFFFF
                                        ),
                                        1.0f to Color(
                                            0xFFFFFFFF
                                        )
                                    )
                                    MissionRewardState.Complete -> gradient04
                                    else -> Brush.linearGradient(
                                        0.0f to Color(
                                            0xFF5C6B70
                                        ),
                                        1.0f to Color(
                                            0xFF5C6B70
                                        )
                                    )
                                }
                            )
                            .clickable {
                                navController.navigate(Route.TodayMission.route)
                            }
                    ) {
                        Row(
                            verticalAlignment = CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            when (rewardedState) {
                                MissionRewardState.Incomplete -> {
                                    Text(
                                        text = "참여하고 투표권받기",
                                        style = FanwooriTypography.subtitle4,
                                        modifier = Modifier.textBrush(
                                            gradient04
                                        )
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_top),
                                        contentDescription = null,
                                        tint = Secondary500,
                                        modifier = Modifier.rotate(90f)
                                    )
                                }
                                MissionRewardState.Complete -> {
                                    Text(
                                        text = "지금 바로 보상받기",
                                        style = FanwooriTypography.subtitle4,
                                        color = Color.White
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_top),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.rotate(90f)
                                    )
                                }
                                else -> {
                                    Text(
                                        text = "일일 미션 완료!",
                                        style = FanwooriTypography.subtitle4,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(34.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Gray700),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_info),
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "무료충전을 통해 얻은 투표권은 오늘까지 사용가능해요.",
                style = FanwooriTypography.caption2,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            FreeChargeItem(
                icon = R.drawable.charge_calender,
                bgColor = Primary50,
                title = "출석 체크 (200 투표권)",
                count = if (attendanceState) 0 else 1
            ) {
                navController.navigate(Route.AttendanceCheck.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            FreeChargeItem(
                icon = R.drawable.charge_video,
                bgColor = Secondary50,
                title = "동영상 광고 (최대 6,000 투표권)",
                count = 20 - adCount
            ) {
                navController.navigate("${Route.VideoAd.route}/${20 - adCount}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            FreeChargeItem(
                icon = R.drawable.charge_roulette,
                bgColor = Primary50,
                title = "행운 룰렛 (최대 30,000 투표권)",
                count = rouletteCount
            ) {
                navController.navigate(Route.LuckyRoulette.route)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FreeChargeItem(icon: Int, bgColor: Color, title: String, count: Int, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .background(Color.White)
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(bgColor)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Center
                    )
                    .size(48.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
                .align(CenterVertically)
        ) {
            Text(text = title, style = FanwooriTypography.subtitle2, color = Gray800)
            Text(
                text = "${count}회 남음",
                style = FanwooriTypography.body2,
                color = if (count == 0) Gray600 else Primary500
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray700,
            modifier = Modifier.align(CenterVertically)
        )
    }
}

@Composable
fun StoreView(
    purchaseHelper: PurchaseHelper,
    viewModel: ChargeHomeViewModel
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val unLimitedTickets by viewModel.userTicketManager.expiredUnlimited.collectAsState(initial = 0)
    val todayTickets by viewModel.userTicketManager.expiredToday.collectAsState(initial = 0)
    val refreshState by purchaseHelper.refreshState.collectAsState()
    var showSuccessDialog by remember {
        mutableStateOf(false)
    }
    var dialogMessage by remember {
        mutableStateOf("")
    }


    LaunchedEffect(key1 = refreshState, block = {
        if (refreshState == RefreshTicket.Need) {
            viewModel.getVoteTickets(purchaseHelper)
        }

    })

    if (showSuccessDialog) {
        VerticalDialog(
            contentText = dialogMessage, buttonOneText = "확인"
        ) {
            showSuccessDialog = false
        }
    }


    LazyColumn(
        Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            MyVote(unlimitedTickets = unLimitedTickets ?: 0, todayTickets = todayTickets ?: 0)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "전체상품", style = FanwooriTypography.body3, color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(InAppProduct.values().size) { index ->

            StoreItem(
                onItemClick = { product ->
                    purchaseHelper.makePurchaseInApp(billingType = BillingClient.ProductType.INAPP,
                        inAppProduct = product,
                        listener = {
                            when (it) {
                                BillingResponse.Success -> {
                                    showSuccessDialog = true
                                    dialogMessage = BillingResponse.Success.message
                                }

                                BillingResponse.Fail -> {
                                    showSuccessDialog = true
                                    dialogMessage = BillingResponse.Fail.message
                                }
                            }
                        })
                }, product = InAppProduct.values()[index]
            )
        }
    }
}