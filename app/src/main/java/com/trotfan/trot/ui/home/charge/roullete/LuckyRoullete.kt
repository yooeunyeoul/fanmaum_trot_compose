package com.trotfan.trot.ui.home.charge.roullete

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fanmaum.roullete.SpinWheel
import com.fanmaum.roullete.SpinWheelDefaults
import com.fanmaum.roullete.SpinWheelVisibleState
import com.fanmaum.roullete.state.rememberSpinWheelState
import com.google.protobuf.Api
import com.trotfan.trot.R
import com.trotfan.trot.di.ApiResult
import com.trotfan.trot.model.LuckyTicket
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.charge.viewmodel.RouletteViewModel
import com.trotfan.trot.ui.home.charge.viewmodel.TicketKind
import com.trotfan.trot.ui.signup.components.VerticalDialogReceiveGift
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.composableActivityViewModel
import kotlinx.coroutines.launch
import java.lang.Exception


val screenPadding = 24.dp

@OptIn(ExperimentalTextApi::class)
@Composable
fun luckyRoulette(
    navController: NavController,
    viewModel: RouletteViewModel = hiltViewModel(),
) {

    var dialogShowing by remember {
        mutableStateOf(false)
    }
    val happyTicket by viewModel.luckyTicket.collectAsState()

    val visibleState by viewModel.visibleState.collectAsState()
    val resultDegree by viewModel.resultDegree.collectAsState()
    val remainTime by viewModel.remainingTime.collectAsState()


    val textList by remember {
        mutableStateOf(
            listOf(
                "본 이벤트는 팬마음 회원 대상으로 진행되며, 팬마음 회원 로그인 및 전자금융거래 이용약관, 개인정보 수집 이용 동의 시에만 지급 및 사용이 가능합니다.",
                "행운 룰렛은 1일 최대 3회 참여 가능하며, 참여 완료 시 투표권 획득이 가능합니다.",
                "행운 룰렛은 4시간에 1회 참여 가능합니다.",
                "지급된 투표권은 당일 자정에 소멸되며, 소멸된 투표권은 복구되지 않습니다.",
                "본 이벤트는 사정에 따라 변경되거나 조기종료될 수 있으며, 변경 및 종료에 관한 내용은 공지사항을 통해 안내됩니다.",
                "팬마음 정책에 어긋나거나 부정한 방법으로 이벤트 참여가 의심되는 경우, 투표권은 지급되지 않으며 지급된 투표권은 회수 처리됩니다.",
                "투표권 미지급 관련 문의는 마이페이지 > 문의하기를 통해 가능합니다."
            )
        )
    }
    val iconList by remember {
        mutableStateOf(
            listOf(
                R.drawable.charge_roulette10000,// 300 ~ 360
                R.drawable.charge_roulette1000,// 240 ~ 300
                R.drawable.charge_roulette200,// 180 ~ 240
                R.drawable.charge_roulette500,// 120 ~ 180
                R.drawable.charge_roulette200,// 60 ~ 120
                R.drawable.charge_roulette500,// 0~ 60
            )
        )
    }

    var pieColorListState by remember {
        mutableStateOf(
            listOf(
                Color(0xFFFFF5D3),
                Color(0xFFFFFFFF),
                Color(0xFFFFF5D3),
                Color(0xFFFFFFFF),
                Color(0xFFFFF5D3),
                Color(0xFFFFFFFF)
            )
        )
    }

    val endPieColorList = listOf(
        Gray100,
        Color.White,
        Gray100,
        Color.White,
        Gray100,
        Color.White
    )

    val state = rememberSpinWheelState(
        pieCount = TicketKind.values().count(),
        durationMillis = 5000,
        resultDegree = resultDegree,
        startDegree = 30f
    )
    val scope = rememberCoroutineScope()

    if (dialogShowing) {
        VerticalDialogReceiveGift(
            contentText = "행운룰렛 당첨!",
            gradientText = "${NumberComma.decimalFormat.format(happyTicket.rouletteQuantity)} 투표권",
            buttonOneText = "확인"
        ) {
            dialogShowing = false
//            pieColorListState = endPieColorList
//            visibleState = SpinWheelVisibleState.Waiting
            viewModel.testRoulette()
        }

    }

    LaunchedEffect(key1 = visibleState, block = {
        if (visibleState == SpinWheelVisibleState.Waiting || visibleState == SpinWheelVisibleState.UnAvailable) {
            state.reset()
        }
    })
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AppbarMLeftIcon(
                title = "행운룰렛",
                icon = R.drawable.icon_back,
                modifier = Modifier.background(color = Primary50)
            )

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFFFF5F5),
                                Color(0xFFFFDBDD)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {

                    Image(
                        painter = painterResource(id = R.drawable.charge_rolettetitle),
                        contentDescription = null,
                        modifier = Modifier
                            .width(360.dp)
                            .height(112.dp)
                    )
                    Text(
                        text = "매일 최대",
                        color = Gray900,
                        style = FanwooriTypography.button1
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_vote_iconcolored),
                            contentDescription = null,
                        )
                        Text(
                            text = " 30,000 투표권 ",
                            style = TextStyle(
                                brush = gradient04, fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                letterSpacing = (-0.25).sp
                            )
                        )
                        Text(
                            text = "받으세요!",
                            color = Gray900,
                            style = FanwooriTypography.button1
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SpinWheel(
                        state = state,
                        dimensions = SpinWheelDefaults.spinWheelDimensions(
                            spinWheelSize = if (screenWidth >= 500.dp) 500.dp else screenWidth - screenPadding,
                            frameWidth = 40.dp
                        ),
                        onClick = {
                            scope.launch {
                                state.animate { pieIndex ->
                                    Log.e("pieIndex", "${pieIndex}")
                                    dialogShowing = true
                                }
                            }
                        },
                        colors = SpinWheelDefaults.spinWheelColors(
                            frameColor = Color(0xFF403d39),
                            pieColors = pieColorListState
                        ),
                        selectorIcon = R.drawable.charge_roulette_stoper,
                        frameRes = if (visibleState == SpinWheelVisibleState.Available) R.drawable.roulettebg
                        else R.drawable.roulette_bg_dim,
                        spinWheelVisibleState = visibleState,
                        content = { pieIndex ->
                            when (visibleState) {
                                SpinWheelVisibleState.Available -> {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = iconList[pieIndex]),
                                            contentDescription = null,
                                            modifier = Modifier.size(width = 72.dp, height = 48.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.charge_roulettevote),
                                            contentDescription = null,
                                            modifier = Modifier.size(width = 24.dp, height = 24.dp)
                                        )
                                    }

                                }
                                else -> {
                                    pieColorListState = endPieColorList
                                }
                            }
                        },
                        spinWheelCenterContent = {
                            when (visibleState) {
                                SpinWheelVisibleState.Available -> {
                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width = 4.dp,
                                                color = Color.White,
                                                shape = CircleShape
                                            )
                                            .shadow(
                                                elevation = 12.dp,
                                                shape = CircleShape,
                                                ambientColor = Color(0xFFD0515F)
                                            )
                                            .size(78.dp)
                                            .zIndex(2f), contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.bg02),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(70.dp)

                                        )
                                        Text(
                                            text = "Go!",
                                            color = Color.White,
                                            style = FanwooriTypography.h1
                                        )

                                    }

                                }
                                SpinWheelVisibleState.Waiting -> {
                                    Column(
                                        modifier = Modifier.zIndex(2f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "다음 룰렛까지",
                                            color = Gray900,
                                            style = FanwooriTypography.subtitle1
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = remainTime,
                                            color = Primary500,
                                            style = FanwooriTypography.h4
                                        )
                                    }

                                }
                                SpinWheelVisibleState.UnAvailable -> {
                                    Column(
                                        modifier = Modifier.zIndex(2f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "남은 횟수가 없어요",
                                            color = Gray900,
                                            style = FanwooriTypography.h3
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "내일 다시 만나요!",
                                            color = Gray750,
                                            style = FanwooriTypography.body4
                                        )
                                    }


                                }
                            }

                        }
                    )


                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier
                            .background(
                                color = Gray900, shape = RoundedCornerShape(
                                    22.dp
                                )
                            )
                            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "오늘 남은 횟수",
                            color = Color.White,
                            style = FanwooriTypography.body5
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${happyTicket.chance} / 3 ",
                            color = textYellow,
                            style = FanwooriTypography.h1
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {

                    Column(
                        modifier = Modifier
                            .background(color = Primary30)
                            .padding(start = 24.dp, end = 24.dp)
                    ) {

                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "상세안내 ",
                            color = Gray700,
                            style = FanwooriTypography.subtitle3
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        textList.forEach {
                            ItemRow(text = it)
                        }
                    }
                }

            }


        }

    }
}

@Composable
fun ItemRow(text: String) {
    Row(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "-",
            color = Gray750,
            style = FanwooriTypography.body2
        )
        Text(
            text = text,
            color = Gray750,
            style = FanwooriTypography.caption2
        )
    }
}
