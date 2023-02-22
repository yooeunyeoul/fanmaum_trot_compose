package com.trotfan.trot.ui.home.charge.mission

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.RewardedVideoListener
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.components.snackbar.CustomSnackBarHost
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.mypage.setting.HyphenText
import com.trotfan.trot.ui.signup.components.VerticalDialogReceiveGift
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.composableActivityViewModel
import com.trotfan.trot.ui.utils.textBrush
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun VideoAd(
    navController: NavController?,
    count: Int? = 0,
    videoViewModel: ChargeHomeViewModel = composableActivityViewModel("ChargeHomeViewModel")
) {
    val configuration = LocalConfiguration.current
    val itemWidth = (configuration.screenWidthDp.dp - 56.dp) / 4
    val adCount by videoViewModel.videoCount.collectAsState()
    val scrollState = rememberScrollState()
    val infoList = listOf(
        "본 이벤트는 팬마음 회원 대상으로 진행되며, 팬마음 회원 로그인 및 전자금융거래 이용약관, 개인정보 수집 이용 동의 시에만 지급 및 사용이 가능합니다.",
        "동영상 광고는 하루에 20 번 볼 수 있습니다.",
        "동영상 광고 시청 시 투표권 300 장을 받을 수 있습니다.",
        "동영상을 끝까지 보지 않으면 투표권을 받을 수 없습니다.",
        "지급된 투표권은 당일 자정에 소멸되며, 소멸된 투표권은 복구되지 않습니다.",
        "지급된 투표권은 마이페이지 > 내 투표권에서 확인하실 수 있습니다.",
        "본 이벤트는 사정에 따라 변경되거나 조기종료될 수 있으며, 변경 및 종료에 관한 내용은 공지사항을 통해 안내됩니다.",
        "팬마음 정책에 어긋나거나 부정한 방법으로 이벤트 참여가 의심되는 경우, 투표권은 지급되지 않으며 지급된 투표권은 회수 처리됩니다.",
        "투표권 미지급 관련 문의는 마이페이지 > 문의하기를 통해 가능합니다."
    )
    val scaffoldState = rememberScaffoldState()
    var missionSnackBarState by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var dialogShowing by remember {
        mutableStateOf(false)
    }

    IronSource.setRewardedVideoListener(object : RewardedVideoListener {
        override fun onRewardedVideoAdOpened() {
        }

        override fun onRewardedVideoAdClosed() {
        }

        override fun onRewardedVideoAvailabilityChanged(p0: Boolean) {
        }

        override fun onRewardedVideoAdStarted() {
        }

        override fun onRewardedVideoAdEnded() {
        }

        override fun onRewardedVideoAdRewarded(p0: Placement?) {
            videoViewModel.postRewardVideo()
            dialogShowing = true
        }

        override fun onRewardedVideoAdShowFailed(p0: IronSourceError?) {
        }

        override fun onRewardedVideoAdClicked(p0: Placement?) {
        }

    })

    BackHandler {
        navController?.previousBackStackEntry?.savedStateHandle?.set(
            "count",
            20 - (adCount ?: 0)
        )
        navController?.popBackStack()
    }

    if (dialogShowing) {
        VerticalDialogReceiveGift(
            contentText = "동영상 광고 시청 완료!",
            gradientText = "${NumberComma.decimalFormat.format(300)} 투표권",
            buttonOneText = "확인"
        ) {
            dialogShowing = false
            if (adCount == 1) {
                missionSnackBarState = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            AppbarMLeftIcon(
                title = "동영상 광고", icon = R.drawable.icon_back, modifier = Modifier.background(
                    Secondary50
                ), onIconClick = {
                    navController?.previousBackStackEntry?.savedStateHandle?.set(
                        "count",
                        20 - (adCount ?: 0)
                    )
                    navController?.popBackStack()
                }

            )
        },
        snackbarHost = CustomSnackBarHost
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = gradient05),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    Image(
                        painter = painterResource(id = R.drawable.charge_video_title),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1회 시청 시 300투표권 x 20회 ",
                        style = FanwooriTypography.button1,
                        color = Gray800
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "매일 최대", style = FanwooriTypography.button1, color = Gray800)
                        Spacer(modifier = Modifier.width(2.dp))
                        Image(
                            painter = painterResource(id = R.drawable.icon_vote),
                            contentDescription = null
                        )
                        Text(
                            text = "6,000 투표권",
                            style = FanwooriTypography.subtitle4,
                            modifier = Modifier
                                .textBrush(
                                    gradient04
                                )
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "받으세요!", style = FanwooriTypography.button1, color = Gray800)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            items(20) { index ->
                                if (index < adCount!!) {
                                    CompleteButton(height = itemWidth)
                                } else if (index == adCount) {
                                    PlayButton(height = itemWidth)
                                } else {
                                    LockButton(height = itemWidth)
                                }
                            }
                        },
                        modifier = Modifier
                            .heightIn(configuration.screenWidthDp.dp, 1200.dp)
                            .padding(start = 16.dp, end = 16.dp),
                        userScrollEnabled = false
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 32.dp)
                ) {
                    Text(
                        text = "상세안내",
                        style = FanwooriTypography.subtitle3,
                        color = Gray700,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                    for (i in infoList) {
                        Spacer(modifier = Modifier.height(12.dp))
                        HyphenText(
                            first = "-",
                            text = i,
                            color = Gray700
                        )
                    }
                }
            }
        }

        if (missionSnackBarState) {
            val previousQueueIndex = navController!!.backQueue.size.minus(2)
            coroutineScope.launch {
                missionSnackBarState =
                    when (scaffoldState.snackbarHostState.showSnackbar("일일 미션 하고 투표권 받기", "더보기")) {
                        SnackbarResult.Dismissed -> {
                            false
                        }
                        SnackbarResult.ActionPerformed -> {
                            if (navController.backQueue[previousQueueIndex].destination.route.toString() == Route.TodayMission.route) {
                                navController.popBackStack()
                            } else {
                                navController.navigate(Route.TodayMission.route) {
                                    popUpTo(navController.currentDestination?.route.toString()) {
                                        inclusive = true
                                    }
                                }
                            }
                            false
                        }
                    }
            }
        }
    }
}

@Composable
fun PlayButton(height: Dp) {
    var failDialogState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .height(height)
            .border(
                width = 3.dp,
                brush = gradient04,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Primary100)
            .clickable {
                if (IronSource.isRewardedVideoAvailable()) {
                    IronSource.showRewardedVideo()
                } else {
                    failDialogState = true
                }
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.charge_videoplay),
                contentDescription = null
            )
            Text(text = "재생", style = FanwooriTypography.button1, color = Primary700)
        }
    }

    if (failDialogState) {
        VerticalDialog(
            contentText = "잠깐!\n" +
                    "동영상 광고를 불러올 수 없어요.\n" +
                    "잠시 후 다시 시도해주세요.", buttonOneText = "확인"
        ) {
            failDialogState = false
        }
    }
}

@Composable
fun CompleteButton(height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Gray500)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_check),
                tint = Gray700,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(text = "완료", style = FanwooriTypography.button1, color = Gray700)
        }
    }
}

@Composable
fun LockButton(height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Gray200)
    ) {
        Image(
            painter = painterResource(id = R.drawable.charge_lock),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun VideoAdPreview() {
    FanwooriTheme {
        VideoAd(null)
    }
}