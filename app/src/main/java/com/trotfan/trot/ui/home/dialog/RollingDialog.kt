package com.trotfan.trot.ui.home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

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
        val state = rememberPagerState()

        LaunchedEffect(state) {
            state.scrollToPage(500)
        }


        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            HorizontalPager(
                count = 1000,
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(468.dp)
            ) { page ->
                when (page % 3) {
                    0 -> {
                        slideImage =
                            "https://1.bp.blogspot.com/-ohzbOYsNNEg/YD-q0fm_hjI/AAAAAAAATDM/Vh-kTEXeFk0UWFY-0EfUo-ex0-TEXlRjwCLcBGAsYHQ/s0/155102157_442051980379472_3136584645181513799_n.jpg"
                    }
                    1 -> {
                        slideImage =
                            "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg"
                    }
                    2 -> {
                        slideImage =
                            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/iz/2021/09/10/vCLCFJbUFOdS637668330000252438.jpg"
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(slideImage)
                            .crossfade(true).build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    )
                }
            }
        }


        DotsIndicator(
            totalDots = 3,
            selectedIndex = state.currentPage % 3,
            selectedColor = Color.White,
            unSelectedColor = Color.LightGray
        )
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