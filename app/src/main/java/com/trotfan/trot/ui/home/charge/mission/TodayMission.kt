package com.trotfan.trot.ui.home.charge.mission

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.composableActivityViewModel
import com.trotfan.trot.ui.utils.drawColoredShadow
import com.trotfan.trot.ui.utils.textBrush

@Composable
fun TodayMission(
    navController: NavController? = null,
    viewModel: ChargeHomeViewModel = composableActivityViewModel(key = "ChargeHomeViewModel")
) {
    val missionState by viewModel.missionState.collectAsState()
    val attendanceState by viewModel.attendanceState.collectAsState()
    val starShareState by viewModel.starShareState.collectAsState()
    val videoRewardState by viewModel.videoRewardState.collectAsState()
    val rouletteState by viewModel.rouletteState.collectAsState()
    val missionCompleteCount by viewModel.missionCompleteCount.collectAsState()

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

                MissionItem(title = "출석체크하기 1회", number = 1, state = attendanceState) {
                    navController?.navigate(Route.AttendanceCheck.route)
                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "내 스타 공유하기 1회", number = 2, state = starShareState) {

                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "동영상광고 보기 1회", number = 3, state = videoRewardState) {
                    navController?.navigate("${Route.VideoAd.route}/20")
                }
                Spacer(modifier = Modifier.height(12.dp))
                MissionItem(title = "행운의 룰렛 돌리기 1회", number = 4, state = rouletteState) {

                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 88.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "일일미션 완료 현황", style = FanwooriTypography.body3, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "미션완료",
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
                            shadowRadius = if (missionCompleteCount == 4) 10.dp else 0.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .width(280.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .alpha(0.93f)
                            .background(
                                Color.White
                            )
                            .align(Center)
                    ) {
                        if (missionCompleteCount == 4) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
                                    text = "3,200투표권 받기",
                                    style = FanwooriTypography.subtitle4,
                                    modifier = Modifier
                                        .textBrush(
                                            gradient04
                                        )
                                        .padding(top = 17.5.dp, bottom = 17.5.dp)
                                )
                            }
                        } else {
                            Text(
                                text = "미션 완료하고  3,200투표권 받기",
                                style = FanwooriTypography.subtitle4,
                                modifier = Modifier
                                    .textBrush(
                                        gradient01
                                    )
                                    .padding(top = 17.5.dp, bottom = 17.5.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }

        AppbarMLeftIcon(
            title = "일일미션",
            icon = R.drawable.icon_back,
            textColor = Color.White,
            iconColor = Color.White
        )
    }
}

@Composable
fun MissionItem(title: String, number: Int, state: Boolean, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .widthIn(0.dp, 500.dp)
            .height(80.dp)
            .padding(start = 32.dp, end = 32.dp),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clickable { onItemClick() }
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Primary100)
                    ) {
                        Text(
                            text = "미션 $number",
                            style = FanwooriTypography.button2,
                            color = Primary800,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = 6.dp, end = 6.dp, top = 1.dp, bottom = 1.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = title, style = FanwooriTypography.subtitle2, color = Gray800)
                }
            }
            Box(
                modifier = Modifier
                    .width(92.dp)
                    .fillMaxHeight()
                    .background(if (state) Color.White else Primary500)
                    .align(CenterEnd)
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
                        verticalAlignment = Alignment.CenterVertically
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