package com.trotfan.trot.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.components.button.BtnOutlineLWhite
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.dialog.DotsIndicator
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray900
import com.trotfan.trot.ui.theme.Primary500
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tutorial(
    navController: NavController? = null
) {
    val state = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray900),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = 4,
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                when (it) {
                    0 -> TutorialComponent(
                        text = "내 스타의 순위를\n실시간으로 확인하기",
                        image = R.drawable.walkthrough01,
                        topMargin = 58
                    )

                    1 -> TutorialComponent(
                        text = "내 스타에게 투표하기",
                        image = R.drawable.walkthrough02,
                        topMargin = 87
                    )

                    2 -> TutorialComponent(
                        text = "다양한 방법으로\n" +
                                "투표권 충전하기",
                        image = R.drawable.walkthrough03,
                        topMargin = 58
                    )

                    3 -> TutorialComponent(
                        text = "투표를 통하여\n" +
                                "내 스타에게 광고를 선물하세요!",
                        image = R.drawable.walkthrough04,
                        topMargin = 58
                    )
                }
            }
        }

        DotsIndicator(
            totalDots = 4,
            selectedIndex = state.currentPage,
            selectedColor = Primary500,
            unSelectedColor = Gray200
        )

        if (state.currentPage < 3) {
            BtnOutlineLWhite(text = "다음", modifier = Modifier.padding(32.dp)) {
                coroutineScope.launch {
                    state.animateScrollToPage(state.currentPage.plus(1))
                }
            }
        } else {
            BtnFilledLPrimary(text = "팬마음 시작하기", modifier = Modifier.padding(32.dp)) {
                navController?.navigate(HomeSections.Vote.route) {
                    popUpTo(Route.Tutorial.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun TutorialComponent(text: String, image: Int, topMargin: Int) {
    Column(
        modifier = Modifier
            .padding(top = topMargin.dp, bottom = 18.dp)
            .widthIn(0.dp, 500.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = FanwooriTypography.h5,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true).build(),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun TutorialPreview() {
    FanwooriTheme {
        Tutorial()
    }
}