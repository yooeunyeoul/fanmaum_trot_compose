package com.trotfan.trot.ui.home.ranking.history.component.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.ui.theme.FanwooriTypography

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RankingHistoryBanner(
    viewModel: RankingHistoryViewModel = viewModel()
) {
    val pagerState = rememberPagerState()
    val banners = viewModel.banners.collectAsState(initial = listOf()).value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        HorizontalPager(
            count = banners?.size ?: 0,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            state = pagerState
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 4.dp, end = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(banners?.get(page)?.image)
                        .crossfade(true).build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null
                )
            }
        }

        banners?.size?.let {
            Box(
                modifier = Modifier
                    .padding(end = 40.dp, bottom = 8.dp)
                    .width(44.dp)
                    .height(26.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(16.dp))
                    .alpha(0.3f)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .padding(end = 40.dp, bottom = 6.dp)
                    .width(44.dp)
                    .height(26.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "${pagerState.currentPage.plus(1)}/${banners.size}",
                    letterSpacing = 2.sp,
                    color = Color.White,
                    style = FanwooriTypography.body2,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun RankingHistoryBannerPreview() {
    FanwooriTheme {
        RankingHistoryBanner()
    }
}