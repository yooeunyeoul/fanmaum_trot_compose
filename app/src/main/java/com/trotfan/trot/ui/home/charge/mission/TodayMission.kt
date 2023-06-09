package com.trotfan.trot.ui.home.charge.mission

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.chip.ChipFilledSPrimary
import com.trotfan.trot.ui.components.dialog.HorizontalImageDialog
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.charge.viewmodel.MissionRewardState
import com.trotfan.trot.ui.home.vote.voteTopShareText
import com.trotfan.trot.ui.components.dialog.VerticalDialogReceiveGift
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodayMission(
    navController: NavController? = null,
    viewModel: ChargeHomeViewModel = composableActivityViewModel(key = "ChargeHomeViewModel")
) {
    val context = LocalContext.current
    val attendanceState by viewModel.attendanceState.collectAsState()
    val starShareState by viewModel.starShareState.collectAsState()
    val videoRewardState by viewModel.videoRewardState.collectAsState()
    val rouletteState by viewModel.rouletteState.collectAsState()
    val missionCompleteCount by viewModel.missionCompleteCount.collectAsState()
    val rewardedState by viewModel.rewardedState.collectAsState()
    val starName by viewModel.starName.collectAsState()
    val simpleDateFormat = SimpleDateFormat("dd")
    val dateString = simpleDateFormat.format(Date()).toString()
    val localDate by viewModel.lastApiTime.collectAsState()
    val dialogShowing by viewModel.missionRewardDialogState.collectAsState()
    val missionCompleteChargeState by viewModel.missionCompleteChargePopupState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val sharedLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (starShareState.not()) {
                viewModel.postShareStar(true)
            }
        }

    LaunchedEffect(key1 = dateString, block = {
        if (dateString != localDate) {
            viewModel.getMissions()
        }

    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
    ) {
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Column(horizontalAlignment = CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.charge_missionbg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )


                Spacer(modifier = Modifier.height(40.dp))

                MissionItem(title = "출석 체크하기 1회", number = 1, state = attendanceState) {
                    navController?.navigate(Route.AttendanceCheck.route)
                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "내 스타 공유하기 1회", number = 2, state = starShareState) {
                    addDynamicLink(
                        "내 스타 공유",
                        "https://play.google.com/store/apps/details?id=com.trotfan.trot",
                        "내 스타 공유하기"
                    ) {
                        Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                voteTopShareText(starName, it.shortLink.toString())
                            )

                            type = "text/plain"
                            val shareIntent = Intent.createChooser(this, null)
                            sharedLauncher.launch(shareIntent)
                        }

                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "동영상 광고 보기 1회", number = 3, state = videoRewardState) {
                    navController?.navigate("${Route.VideoAd.route}/20")
                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "행운 룰렛 돌리기 1회", number = 4, state = rouletteState) {
                    navController?.navigate(Route.LuckyRoulette.route)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 88.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "일일 미션 완료 현황", style = FanwooriTypography.body3, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "미션 완료",
                        style = FanwooriTypography.h3,
                        color = Color.White
                    )
                    Text(
                        text = "$missionCompleteCount/4",
                        style = FanwooriTypography.h3,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .drawColoredShadow(
                            color = Primary500,
                            alpha = 0.93f,
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            shadowRadius = if (rewardedState == MissionRewardState.Complete) 10.dp else 0.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .width(296.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .alpha(0.93f)
                            .background(
                                if (rewardedState == MissionRewardState.Rewarded) Gray300 else Color.White
                            )
                            .align(Center)
                    ) {
                        when (rewardedState) {
                            MissionRewardState.Incomplete -> {
                                Text(
                                    text = "미션 완료하고  3,200 투표권 받기",
                                    style = FanwooriTypography.subtitle4,
                                    modifier = Modifier
                                        .textBrush(
                                            gradient01
                                        )
                                        .align(Center)
                                )
                            }
                            MissionRewardState.Complete -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.postMission()
                                        },
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "눌러서",
                                        style = FanwooriTypography.subtitle4,
                                        color = Primary500
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_vote),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Text(
                                        text = "3,200 투표권 받기",
                                        style = FanwooriTypography.subtitle4,
                                        modifier = Modifier
                                            .textBrush(
                                                gradient04
                                            )
                                            .padding(top = 17.5.dp, bottom = 17.5.dp)
                                    )
                                }
                            }
                            else -> {
                                Text(
                                    text = "일일 미션 보상완료",
                                    style = FanwooriTypography.subtitle4,
                                    color = Gray650,
                                    modifier = Modifier
                                        .padding(top = 17.5.dp, bottom = 17.5.dp)
                                        .align(Center)
                                )
                            }
                        }
                    }
                }
            }
        }

        AppbarMLeftIcon(
            title = "일일 미션",
            icon = R.drawable.icon_back,
            textColor = Color.White,
            iconColor = Color.White,
            onIconClick = {
                navController?.popBackStack()
            }
        )

        if (dialogShowing) {
            VerticalDialogReceiveGift(
                contentText = "일일 미션 완료!",
                gradientText = "${NumberComma.decimalFormat.format(3200)} 투표권",
                buttonOneText = "확인"
            ) {
                coroutineScope.launch {
                    viewModel.missionRewardDialogState.emit(false)
                    viewModel.missionCompleteChargePopupState.emit(true)
                }
            }
        }

        if (missionCompleteChargeState) {
            HorizontalImageDialog(
                onPositive = {
                    coroutineScope.launch {
                        navController?.popBackStack()
                        viewModel.missionCompleteChargePopupState.emit(false)
                    }
                },
                onDismiss = {
                    coroutineScope.launch {
                        viewModel.missionCompleteChargePopupState.emit(false)
                    }
                }
            )
        }
    }
}

@Composable
fun MissionItem(title: String, number: Int, state: Boolean, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .widthIn(0.dp, 500.dp)
            .height(116.dp)
            .padding(start = 32.dp, end = 32.dp),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable { onItemClick() }
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                ChipFilledSPrimary(
                    modifier = Modifier,
                    text = "미션 $number"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = title,
                    style = FanwooriTypography.subtitle2,
                    color = Gray800,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(96.dp)
                    .fillMaxHeight()
                    .background(if (state) Color.White else Primary500)
            ) {
                if (state) {
                    Box(
                        modifier = Modifier
                            .align(CenterStart)
                            .height(49.dp)
                            .width(1.dp)
                            .background(
                                Gray200
                            )
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_check),
                            contentDescription = null,
                            tint = Gray200,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "완료", style = FanwooriTypography.subtitle2, color = Gray400)
                    }
                } else {
                    Text(
                        text = "바로가기",
                        style = FanwooriTypography.subtitle2,
                        color = Color.White,
                        modifier = Modifier.align(Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TodayMissionPreview() {
    FanwooriTheme {
        TodayMission()
    }
}