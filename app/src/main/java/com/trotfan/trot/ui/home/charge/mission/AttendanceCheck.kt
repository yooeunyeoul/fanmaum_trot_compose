package com.trotfan.trot.ui.home.charge.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.components.snackbar.CustomSnackBarHost
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.mypage.setting.HyphenText
import com.trotfan.trot.ui.signup.components.VerticalDialogReceiveGift
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.composableActivityViewModel
import com.trotfan.trot.ui.utils.textBrush
import kotlinx.coroutines.launch

@Composable
fun AttendanceCheck(
    navController: NavController?,
    chargeHomeViewModel: ChargeHomeViewModel = composableActivityViewModel("ChargeHomeViewModel")
) {
    val scrollState = rememberScrollState()
    val infoList = listOf(
        "본 이벤트는 팬마음 회원 대상으로 진행되며, 팬마음 회원 로그인 및 전자금융거래 이용약관, 개인정보 수집 이용 동의 시에만 지급 및 사용이 가능합니다.",
        "출석 체크 참여 완료 시 보상이 지급됩니다.",
        "지급되는 보상의 종류 및 수량은 변경될 수 있습니다.",
        "지급된 투표권은 당일 자정에 소멸되며, 소멸된 투표권은 복구되지 않습니다.",
        "지급된 투표권은 마이페이지 > 내 투표권에서 확인하실 수 있습니다.",
        "본 이벤트는 사정에 따라 변경되거나 조기종료될 수 있으며, 변경 및 종료에 관한 내용은 공지사항을 통해 안내됩니다.",
        "팬마음 정책에 어긋나거나 부정한 방법으로 이벤트 참여가 의심되는 경우, 투표권은 지급되지 않으며 지급된 투표권은 회수 처리됩니다.",
        "투표권 미지급 관련 문의는 마이페이지 > 문의하기를 통해 가능합니다."
    )
    val attendanceState by chargeHomeViewModel.attendanceState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val dialogShowing by chargeHomeViewModel.attendanceRewardDialogState.collectAsState()
    var missionSnackBarState by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            AppbarMLeftIcon(
                title = "출석 체크", icon = R.drawable.icon_back, modifier = Modifier.background(
                    Primary50
                ), onIconClick = {
                    navController?.popBackStack()
                }
            )
        },
        snackbarHost = CustomSnackBarHost
    ) {
        if (dialogShowing) {
            VerticalDialogReceiveGift(
                contentText = "출석 체크 완료!",
                gradientText = "${NumberComma.decimalFormat.format(200)} 투표권",
                buttonOneText = "확인"
            ) {
                coroutineScope.launch {
                    chargeHomeViewModel.attendanceRewardDialogState.emit(false)
                    missionSnackBarState = true
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary50)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Image(
                    painter = painterResource(id = R.drawable.charge_calender_title),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "매일 출석하고", style = FanwooriTypography.button1, color = Gray800)
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.icon_vote),
                        contentDescription = null
                    )
                    Text(
                        text = "200 투표권",
                        style = FanwooriTypography.subtitle4,
                        modifier = Modifier
                            .textBrush(
                                gradient04
                            )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "받으세요!", style = FanwooriTypography.button1, color = Gray800)
                }

                Spacer(modifier = Modifier.height(48.dp))
                Box {
                    Image(
                        painter = painterResource(id = if (attendanceState) R.drawable.charge_calendercheck02 else R.drawable.charge_calendercheck01),
                        contentDescription = null,
                        modifier = Modifier.size(144.dp)
                    )
                    Text(
                        text = if (attendanceState) "출석완료" else "출석 전",
                        style = FanwooriTypography.subtitle1,
                        color = if (attendanceState) Color.White else Gray750,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                BtnFilledLPrimary(
                    text = if (attendanceState) "출석완료" else "출석하기",
                    modifier = Modifier.width(248.dp),
                    enabled = attendanceState.not()
                ) {
                    chargeHomeViewModel.postAttendance()
                }
                Spacer(modifier = Modifier.height(48.dp))
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

            if (missionSnackBarState) {
                coroutineScope.launch {
                    missionSnackBarState =
                        when (scaffoldState.snackbarHostState.showSnackbar(
                            "일일 미션 하고 투표권 받기",
                            "더보기"
                        )) {
                            SnackbarResult.Dismissed -> {
                                false
                            }
                            SnackbarResult.ActionPerformed -> {
                                navController?.navigate(Route.TodayMission.route)
                                false
                            }
                        }
                }
            }
        }
    }
}

@Preview
@Composable
fun AttendanceCheckPreview() {
    FanwooriTheme {
        AttendanceCheck(null)
    }
}