package com.trotfan.trot.ui.home.ranking

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnOutlineSecondaryLeftIcon
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.home.ranking.components.RankImageItem
import com.trotfan.trot.ui.home.ranking.components.RankItem
import com.trotfan.trot.ui.home.ranking.viewmodel.RankHomeViewModel
import com.trotfan.trot.ui.home.vote.component.ChipCapsuleImg
import com.trotfan.trot.ui.home.vote.tabData
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.launch

enum class RankStatus {
    Available, UnAvailable
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun RankHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RankHomeViewModel = hiltViewModel(),
    lazyListState: LazyListState = rememberLazyListState()
) {
    val favoriteStarGender by viewModel.userInfoManager.favoriteStarGenderFlow.collectAsState(
        initial = Gender.MEN
    )
    var tabIndex by remember { mutableStateOf(if (favoriteStarGender == Gender.MEN) 0 else 1) }
    val isShowingScrollToolTip by viewModel.rankMainManager.isShowingRankMainScrollToolTipFlow.collectAsState(
        initial = false
    )
    val scrollState = rememberScrollState()

    val rankStatus by remember {
        mutableStateOf(RankStatus.Available)
    }

    LaunchedEffect(key1 = lazyListState.isScrollInProgress, block = {

        if (isShowingScrollToolTip) {
            val offset = lazyListState.firstVisibleItemScrollOffset
            if (offset > 150) {
                viewModel.saveScrollTooltipState(false)
            }
            Log.e("OFFSE", offset.toString())
        }
    })

    Box(Modifier.fillMaxSize()) {
        if (isShowingScrollToolTip) {
            ChipCapsuleImg()
        }
        Column(Modifier.fillMaxSize()) {
            AppbarL(
                title = "순위",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyListState
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        HorizontalImagePager(scrollState)
                        LastRankView()
                    }

                }
                when (rankStatus) {
                    RankStatus.Available -> {
                        stickyHeader {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .height(94.dp)
                                    .background(color = Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "12월 현재 순위",
                                    style = FanwooriTypography.h2,
                                    color = Gray900
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "투표마감까지",
                                        style = FanwooriTypography.body2,
                                        color = Gray700
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "24일",
                                        style = FanwooriTypography.button1,
                                        color = Primary500
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "남았습니다",
                                        style = FanwooriTypography.body2,
                                        color = Gray700
                                    )

                                }

                            }

                        }
                        item {

                            val pagerState = rememberPagerState(initialPage = tabIndex)
                            val coroutineScope = rememberCoroutineScope()
                            Box {
                                TabRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp),
                                    backgroundColor = Color.White,
                                    selectedTabIndex = pagerState.currentPage,
                                    divider = {},
                                    // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                                    indicator = { tabPositions ->
                                        Box(
                                            Modifier
                                                .tabIndicatorOffset(tabPositions[tabIndex])
                                                .height(3.dp)
                                                .background(
                                                    color = Primary300,
                                                    shape = RoundedCornerShape(1.5.dp)
                                                )
                                        )
                                    }
                                ) {
                                    tabData.forEachIndexed { index, text ->
                                        Tab(
                                            modifier = Modifier,
                                            selected = tabIndex == index,
                                            onClick = {
                                                coroutineScope.launch {
                                                    tabIndex = index
                                                }
                                            },
                                            text = {

                                                Text(
                                                    text = text,
                                                    style = FanwooriTypography.body3,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = if (tabIndex == index) Primary900 else Gray700,
                                                    fontSize = 17.sp,
                                                    modifier = if (index == 0) Modifier.padding(
                                                        start = 24.dp
                                                    ) else Modifier.padding(
                                                        end = 24.dp
                                                    )
                                                )
                                            })
                                    }
                                }
                                Divider(
                                    thickness = 1.dp, color = Gray200, modifier = Modifier.align(
                                        Alignment.BottomCenter
                                    )
                                )
                            }
                        }
                        items(100) { index ->
                            if (index == 0) {
                                RankImageItem()
                            } else {
                                RankItem(
                                    text = "김쿵야",
                                    subText = 1000,
                                    imageUrl = "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg",
                                    rank = if (index % 3 == 0) 1 else if (index % 3 == 1) 2 else 3,
                                    item = null,
                                    onClick = {

                                    }
                                )

                            }
                        }
                    }
                    RankStatus.UnAvailable -> {
                        item {
                            noRankHistory()
                        }
                    }
                }


            }

        }

    }


}

@Composable
fun noRankHistory() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.ranking_nohistory),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(19.dp))
        Text(
            text = "팬마음 첫 투표 진행중!",
            style = FanwooriTypography.subtitle1,
            color = Gray800
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "순위는",
                style = FanwooriTypography.body5,
                color = Gray700
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "매월 2일부터 공개",
                style = FanwooriTypography.button1,
                color = Primary500
            )
            Text(
                text = "됩니다.",
                style = FanwooriTypography.body5,
                color = Gray700
            )
        }
        Text(
            text = "지금 바로 내 스타에게 투표해보세요!",
            style = FanwooriTypography.body5,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(32.dp))
        BtnOutlineSecondaryLeftIcon(
            text = "투표 하러가기",
            onClick = { /*TODO*/ },
            icon = R.drawable.icon_vote,
            isCircle = false
        )
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalImagePager(scrollState: ScrollState) {
    val pagerState = rememberPagerState(initialPage = 0)
    val pageList = mutableListOf<String>(
        "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg",
        "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg",
        "https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg"
    )

    Box(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            count = pageList.size,
            modifier = Modifier
                .height(120.dp)
                .background(Gray700)
                .nestedScroll(remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            return if (available.y > 0) Offset.Zero else Offset(
                                x = 0f,
                                y = -scrollState.dispatchRawDelta(-available.y)
                            )
                        }
                    }
                })
        ) { page: Int ->

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pageList[page])
                    .crossfade(true).build(),
                contentDescription = null,
                error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                contentScale = ContentScale.FillWidth,
            )

        }

        Box(
            Modifier
                .padding(end = 16.dp, bottom = 8.dp)
                .align(Alignment.BottomEnd)
                .background(GrayOpacity30, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "${pagerState.currentPage + 1}/${pageList.size}",
                style = FanwooriTypography.body2,
                color = Color.White,
                modifier = Modifier.padding(
                    start = 9.dp,
                    end = 9.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }
    }

}

@Composable
fun LastRankView() {

    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Gray100)
            .height(80.dp)
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {

            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(defaultElevation = 2.dp),
            modifier = Modifier.height(56.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color = Secondary100, shape = CircleShape),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_history),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Gray800
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "지난 순위 보기",
                    style = FanwooriTypography.button1,
                    color = Secondary600,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = Gray500
                )


            }

        }

    }

}