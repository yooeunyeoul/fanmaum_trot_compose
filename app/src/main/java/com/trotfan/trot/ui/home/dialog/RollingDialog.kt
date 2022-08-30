package com.trotfan.trot.ui.home.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.ui.theme.FanwooriTypography

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@Composable
fun RollingDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        var slideImage by remember {
            mutableStateOf("https://1.bp.blogspot.com/-ohzbOYsNNEg/YD-q0fm_hjI/AAAAAAAATDM/Vh-kTEXeFk0UWFY-0EfUo-ex0-TEXlRjwCLcBGAsYHQ/s0/155102157_442051980379472_3136584645181513799_n.jpg")
        }

        var color by remember {
            mutableStateOf(Color.Black)
        }
        val state = rememberPagerState()

        LaunchedEffect(state) {
            state.scrollToPage(501)
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    count = 1000,
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(468.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) { page ->
                    when (page % 3) {
                        0 -> {
//                            slideImage =
//                                "https://1.bp.blogspot.com/-ohzbOYsNNEg/YD-q0fm_hjI/AAAAAAAATDM/Vh-kTEXeFk0UWFY-0EfUo-ex0-TEXlRjwCLcBGAsYHQ/s0/155102157_442051980379472_3136584645181513799_n.jpg"
                            color = Color.Black
                        }
                        1 -> {
//                            slideImage =
//                                "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg"
                            color = Color.Yellow
                        }
                        2 -> {
//                            slideImage =
//                                "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/iz/2021/09/10/vCLCFJbUFOdS637668330000252438.jpg"
                            color = Color.Blue
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(slideImage)
//                                .crossfade(true).build(),
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxWidth(),
//                            contentDescription = null
//                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                ) {
                    DotsIndicator(
                        totalDots = 3,
                        selectedIndex = state.currentPage % 3,
                        selectedColor = Color.White,
                        unSelectedColor = Color.LightGray,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "오늘은 하루 보지 않기",
                    color = Color.White,
                    style = FanwooriTypography.button2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                        .clickable { onDismiss() }
                        .align(Alignment.CenterVertically)
                )

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(12.dp)
                        .background(Color.White)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "닫기",
                    color = Color.White,
                    style = FanwooriTypography.button2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                        .clickable { onDismiss() }
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            }
        }
    }
}