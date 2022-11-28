@file:OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)

package com.trotfan.trot.ui.home.vote

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.R
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.ui.Route
import com.trotfan.trot.model.VoteData
import com.trotfan.trot.ui.components.navigation.CustomTopAppBarWithIcon
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.vote.component.*
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.VoteStatus
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.disabledHorizontalPointerInputScroll
import com.trotfan.trot.ui.utils.disabledVerticalPointerInputScroll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.util.*
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VoteHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: VoteHomeViewModel = hiltViewModel(),
    votingBottomSheetState: ModalBottomSheetState
) {
    val context = LocalContext.current
    val voteStatus by viewModel.voteStatus.collectAsState()
    val top3Info by viewModel.top3Info.collectAsState()
    val voteStatusBoardList by viewModel.voteDataList.collectAsState()
    val voteStatusBoardListCount by viewModel.voteDataListCount.collectAsState()
    val favoriteStarGender by viewModel.favoriteStarManager.favoriteStarGenderFlow.collectAsState(
        initial = 0
    )
    val favoriteStarName by viewModel.favoriteStarManager.favoriteStarNameFlow.collectAsState(
        initial = ""
    )


    var ticks by remember { mutableStateOf(7200) }
    val second: String = (ticks.toLong() % 60).toString()
    val minute: String = (ticks.toLong() / 60 % 60).toString()
    val hour: String = (ticks.toLong() / 3600).toString()

    var myVoteHide by remember {
        mutableStateOf(true)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressedLastRank by interactionSource.collectIsPressedAsState()



    LaunchedEffect(ticks != 0) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
    }

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(bottom = 56.dp)
        ) {
            CustomTopAppBarWithIcon(
                title = "일일 투표",
                modifier = Modifier.clickable {
//                viewModel.changeVoteStatus()
                },
                onClickEndIcon = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, voteTopShareText(favoriteStarName)
                        )

                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            )

            Box(modifier = Modifier.height(80.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.board_upper),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 80.dp)
                )

                when (voteStatus) {
                    VoteStatus.Available, VoteStatus.NotVoteForFiveTimes -> {
                        voteToStar(
                            items = voteStatusBoardList,
                            count = voteStatusBoardListCount,
                            voteStatus = voteStatus,
                            viewModel = viewModel
                        )
                    }
                    VoteStatus.VoteEnd -> {
                        voteEndHeader()
                    }
//                VoteStatus.NotVoteForFiveTimes -> {
//                    voteIng()
//                }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Box(modifier = Modifier) {
                        Image(
                            painter = painterResource(id = R.drawable.board_lower),
                            contentScale = ContentScale.FillHeight,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 80.dp)
                        )

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKf_rXYJjWAMYI2PjeXcnljkfIhnFwGQuEPLdj3xg8cYJh7GRYH9XnVM2WwJTAOiWShII&usqp=CAU")
                                .crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 8.dp,
                                    brush = gradient01,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 12.dp,
                                    color = Color.White,
                                    CircleShape
                                )
                                .align(Alignment.Center)
                        )
                    }
                }

                item {

                    when (voteStatus) {
                        VoteStatus.VoteEnd -> {
                            HeaderEndState(myStarName = "이찬원")
                        }
                        VoteStatus.Available, VoteStatus.NotVoteForFiveTimes -> {
                            HeaderVoteState(
                                myStarName = "이찬원",
                                dayRank = 5,
                                monthRank = 9,
                                month = 12
                            )
                        }
                    }

                    MyVote(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        isHide = myVoteHide,
                        hideState = { isHide ->
                            myVoteHide = isHide
                        })
                    Box(
                        Modifier
                            .height(112.dp)
                            .background(color = Color.White)
                    ) {

                        ToolTip(modifier = Modifier.padding(start = 24.dp, top = 8.dp).zIndex(1f))


                        Divider(
                            thickness = 8.dp,
                            color = Gray100,
                            modifier = Modifier.padding(top = 32.dp)
                        )

                        Button(
                            onClick = { /*TODO*/ },
                            shape = RectangleShape,
                            interactionSource = interactionSource,
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (isPressedLastRank) Gray50 else Color.White),
                            modifier = Modifier
                                .height(72.dp)
                                .offset(y = 40.dp),

                            ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(color = Secondary100, shape = CircleShape),
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_ranking),
                                        contentDescription = null,
                                        modifier = Modifier.align(Alignment.Center),
                                        tint = Secondary600
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "최종 순위 선정 방법",
                                    style = FanwooriTypography.button1,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Secondary600,
                                    fontSize = 17.sp,
                                    modifier = Modifier.weight(1f)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.icon_arrow),
                                    contentDescription = null
                                )


                            }

                        }

                    }


                }

                when (voteStatus) {
                    VoteStatus.Available, VoteStatus.NotVoteForFiveTimes -> {
                        item {
                            Divider(thickness = 8.dp, color = Gray100)
                        }
                        stickyHeader {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White)
                                    .padding(top = 24.dp, bottom = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "일일투표 순위",
                                    style = FanwooriTypography.h2,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray800,
                                    fontSize = 22.sp
                                )
                                Spacer(modifier = Modifier.height(17.dp))

                                Row(
                                    Modifier
                                        .padding(start = 24.dp, end = 24.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "마감까지",
                                        style = FanwooriTypography.body2,
                                        fontWeight = FontWeight.Medium,
                                        color = Gray700,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.width(7.dp))
                                    Text(
                                        text = "${if (hour.toInt() < 10) "0${hour}" else hour}:${if (minute.toInt() < 10) "0${minute}" else minute}:${if (second.toInt() < 10) "0${second}" else second}",
                                        style = FanwooriTypography.button1,
                                        color = Primary500,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.width(71.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(7.dp))
                                    Text(
                                        text = "남았습니다",
                                        style = FanwooriTypography.body2,
                                        fontWeight = FontWeight.Medium,
                                        color = Gray700,
                                        fontSize = 15.sp
                                    )


                                }
                            }
                        }
                        item {
                            TodayRankingView(favoriteStarGender ?: 0)

                        }
                    }
                    VoteStatus.VoteEnd -> {
                        item {
                            Column(
                                modifier
                                    .fillMaxWidth()
                                    .background(color = Gray100)
                                    .padding(top = 32.dp, bottom = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.vote_counting),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.height(17.2.dp))
                                Text(
                                    text = "일일투표 집계 중",
                                    style = FanwooriTypography.subtitle2,
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(11.dp))
                                Text(
                                    text = "투표 결과는 자정 이후\n" +
                                            "순위에서 확인할 수 있어요.",
                                    style = FanwooriTypography.caption1,
                                    color = Gray600,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )


                            }
                        }

                    }
                }


            }

        }

    }
}

@Composable
fun TodayRankingView(initPage: Int) {
    val tabData = listOf<String>("남자스타", "여자스타")

    val pagerState = rememberPagerState(
        initialPage = 1
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = Color.White,
        contentColor = Primary300,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[tabIndex])
                    .height(3.dp)
                    .padding(
                        start = if (tabIndex == 0) 24.dp else 0.dp,
                        end = if (tabIndex == 1) 24.dp else 0.dp
                    )
                    .background(color = Primary300, shape = RoundedCornerShape(1.5.dp))
            )

        }, divider = {
            Divider(color = Gray200, modifier = Modifier.height(1.dp))
        },
        modifier = Modifier
            .height(48.dp)
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(modifier = Modifier, selected = tabIndex == index, onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(index)
                }
            }, text = {

                Text(
                    text = text,
                    style = FanwooriTypography.body3,
                    fontWeight = FontWeight.SemiBold,
                    color = if (pagerState.currentPage == index) Primary900 else Gray600,
                    fontSize = 17.sp,
                    modifier = if (index == 0) Modifier.padding(start = 24.dp) else Modifier.padding(
                        end = 24.dp
                    )
                )
            })
        }
    }

    HorizontalPager(
        count = 2,
        state = pagerState,
        modifier = Modifier
            .background(color = Color.White)
            .disabledHorizontalPointerInputScroll()
    ) { index ->


        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(5) {
                if (index == 0) {
                    VoteItem()
                } else {
                    VoteItem(isMyStar = true)
                }

            }
        }


    }

}

fun voteTopShareText(favoriteStarName: String?): String {
    return "#팬마음 ${Calendar.getInstance().get(Calendar.MONTH).plus(1)}월 투표 참여하고\n" +
            "\n" +
            "${favoriteStarName}만을 위한 특별한 광고 한가득 선물하기 \uD83C\uDF81\uD83C\uDF88\n" +
            "\n" +
            " \n" +
            "\n" +
            "내 ${favoriteStarName}는 현재 ❓❔위\n" +
            "\n" +
            " \n" +
            "\n" +
            "\uD83D\uDD3B실시간 순위 보러 가기\uD83D\uDD3B\n" +
            "\n" +
            "투표 링크(딥링크)"
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun voteToStar(
    items: List<VoteData>,
    count: Int,
    voteStatus: VoteStatus,
    viewModel: VoteHomeViewModel
) {


    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = voteStatus, block = {
        while (items.isNotEmpty()) {
            yield()
            delay(3500)
            try {
                Log.e("count", "${items.count()}")

                if (items.count() >= pagerState.currentPage + 1) {
                    pagerState.animateScrollToPage(
//                        page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                        page = pagerState.currentPage + 1
                    )

                }
                viewModel.currentPage = pagerState.currentPage
                Log.e("pagerState.currentPage", "${pagerState.currentPage}")

            } catch (_: Throwable) {

            }
        }
//        if (voteStatus == VoteStatus.NotVoteForFiveTimes) {
//            if (items.isNotEmpty()) {
//                pagerState.animateScrollToPage(
//                    page = items.size - 1
//                )
//            }
//        }
    })

    VerticalPager(
        count = count,
        state = pagerState,
        modifier = Modifier.disabledVerticalPointerInputScroll()
    ) { currentPage ->
//        Log.e("items.size", "${items.size}")
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 18.dp, end = 18.dp)
            ) {
                Text(
                    buildAnnotatedString {

                        when (voteStatus) {
                            VoteStatus.Available -> {
                                if (items[currentPage].quantity == -1) {
                                    Text(
                                        text = "투표 진행 중",
                                        color = Color.White,
                                        style = FanwooriTypography.subtitle4,
                                        maxLines = 1,
                                        fontSize = 18.sp,
                                    )
                                } else {
                                    append("${items[currentPage].user_name}님이")
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        append(" ${items[currentPage].star_name} ")
                                    }
                                    append("님에게 투표했어요!")
                                }
                            }
//                            VoteStatus.NotVoteForFiveTimes -> {
//                                Text(
//                                    text = "투표 진행 중",
//                                    color = Color.White,
//                                    style = FanwooriTypography.subtitle4,
//                                    maxLines = 1,
//                                    fontSize = 18.sp,
//                                )
//
//                            }
                            else -> {}
                        }

                    }, maxLines = 1, style = FanwooriTypography.body2,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(9.5.dp))
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                when (voteStatus) {
                    VoteStatus.Available -> {
                        if (items[currentPage].quantity == -1) {
                            Text(
                                text = "지금 내 스타에게 투표해보세요!",
                                color = Color.White,
                                style = FanwooriTypography.body2,
                                maxLines = 1,
                                fontSize = 15.sp,
                                overflow = TextOverflow.Ellipsis
                            )

                        } else {
                            Text(
                                text = "${items[currentPage].quantity}",
                                color = Color.White,
                                modifier = Modifier.weight(weight = 1f, fill = false),
                                style = FanwooriTypography.subtitle4,
                                maxLines = 1,
                                fontSize = 18.sp,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "표",
                                color = Color.White,
                                style = FanwooriTypography.subtitle4,
                                maxLines = 1,
                                fontSize = 18.sp,
                                overflow = TextOverflow.Visible
                            )
                        }

                    }
//                    VoteStatus.NotVoteForFiveTimes -> {
//                        Text(
//                            text = "지금 내 스타에게 투표해보세요!",
//                            color = Color.White,
//                            style = FanwooriTypography.body2,
//                            maxLines = 1,
//                            fontSize = 15.sp,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }

                    else -> {}
                }


            }


        }

    }

}

@Composable
fun voteIng() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "투표 진행 중",
            color = Color.White,
            style = FanwooriTypography.subtitle4,
            maxLines = 1,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "지금 내 스타에게 투표해보세요!",
            color = Color.White,
            style = FanwooriTypography.body2,
            maxLines = 1,
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis
        )


    }
}

@Composable
fun voteEndHeader() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "투표 마감",
            color = Color.White,
            style = FanwooriTypography.subtitle4,
            maxLines = 1,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "[투표 집계 시간] 23:30:00 ~ 23:59:59",
            color = Color.White,
            style = FanwooriTypography.body2,
            maxLines = 1,
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis
        )


    }
}


