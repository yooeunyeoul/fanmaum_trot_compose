package com.trotfan.trot.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.datastore.dateManager
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.home.charge.ChargeHome
import com.trotfan.trot.ui.home.dialog.AutoVotingDialog
import com.trotfan.trot.ui.home.dialog.FeverTimeDialog
import com.trotfan.trot.ui.home.dialog.RollingDialog
import com.trotfan.trot.ui.home.mypage.home.MyPageHome
import com.trotfan.trot.ui.home.ranking.RankHome
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.home.vote.VoteHome
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.dpToSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class HomeSections(
    val title: String,
    val route: String
) {
    Vote(title = "일일 투표", route = "home/vote"),
    Ranking(title = "순위", route = "home/ranking"),
    Charge(title = "충전", route = "home/charge"),
    MyPage(title = "마이페이지", route = "home/mypage")
}

val BottomNavHeight = 56.dp
private val TextIconSpacing = 2.dp

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewTrotBottomBar() {
//    FanwooriTheme {
//        TrotBottomBar(
//            tabs = HomeSections.values(),
//            currentRoute = HomeSections.Vote,
//            onSelected = {
//
//            }
//        )
//    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrotBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: HomeSections,
    onSelected: (HomeSections) -> Unit,
    lazyListStates: HashMap<String, LazyListState>,
    viewModel: HomeViewModel = viewModel()
) {

    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val mainPopups by viewModel.mainPopups.collectAsState()
    val updateState by viewModel.updateState.collectAsState()
    val rollingState by viewModel.rollingState.collectAsState()
    val feverStatus by viewModel.feverStatus.collectAsState()
    val autoVoteStatus by viewModel.autoVoteStatus.collectAsState()

    LaunchedEffect(key1 = currentRoute, block = {
        onSelected.invoke(currentRoute)
    })


    if (updateState) {
        mainPopups?.update?.let {
            HorizontalDialog(
                titleText = it.title,
                contentText = it.content,
                positiveText = "업데이트",
                negativeText = "다음에",
                onPositive = {
                    viewModel.updateState.value = false
                },
                onDismiss = {
                    coroutineScope.launch {
                        context.dateManager.data.collect { date ->
                            viewModel.rollingState.value =
                                date.rollingDate != LocalDate.now()
                                    .toString() && mainPopups?.layers.isNullOrEmpty().not()
                        }
                    }
                    viewModel.feverStatus.value = mainPopups?.is_rewarded == true
                    viewModel.autoVoteStatus.value = mainPopups?.auto_vote?.is_voted == true
                    viewModel.updateState.value = false
                }
            )
        }
    }

    if (autoVoteStatus) {
        AutoVotingDialog {
            coroutineScope.launch {
                viewModel.autoVoteStatus.emit(false)
            }
        }
    }

    if (feverStatus) {
        FeverTimeDialog {
            coroutineScope.launch {
                viewModel.feverStatus.emit(false)
            }
        }
    }

    if (rollingState) {
        mainPopups?.layers?.let { it ->
            RollingDialog(
                layers = it,
                onDismiss = {
                    coroutineScope.launch {
                        viewModel.rollingState.emit(false)
                    }
                }
            )
        }
    }

    Surface(
        color = Color.White,
        contentColor = Color.Blue,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.height(BottomNavHeight),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { sections ->
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.White)
                        .fillMaxHeight()
                        .selectable(
                            selected = currentRoute.title == sections.title,
                            onClick = {
                                if (sections.route == currentRoute.route) {
                                    Log.e("current ROute", "같다")
                                    coroutineScope.launch {
                                        lazyListStates[sections.route]?.scrollToItem(0)
//                                        lazyListState.scrollToItem(0)
                                    }
                                }
                                onSelected(sections)
                            },
                            interactionSource = MutableInteractionSource(),
                            indication = null

                        )

                ) {
                    Divider(thickness = 1.dp, color = Gray100)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .layoutId("icon")
                            .padding(horizontal = TextIconSpacing),
                        content = {
                            var id = 0
                            when (sections.title) {
                                HomeSections.Vote.title -> {
                                    id = R.drawable.icon_vote
                                }
                                HomeSections.Ranking.title -> {
                                    id = R.drawable.icon_ranking
                                }
                                HomeSections.Charge.title -> {
                                    id = R.drawable.icon_charge
                                }
                                HomeSections.MyPage.title -> {
                                    id = R.drawable.icon_mypage
                                }

                            }
                            Icon(
                                painter = painterResource(id = id),
                                modifier = Modifier.height(18.3.dp),
                                contentDescription = null,
                                tint = if (currentRoute.title == sections.title) Primary500 else Gray500
                            )
                        }

                    )
                    Spacer(modifier = Modifier.height(2.5.dp))
                    Box(
                        modifier = Modifier
                            .layoutId("text")
                            .padding(horizontal = TextIconSpacing),
                        content = {
                            Text(
                                text = sections.title,
                                fontSize = dpToSp(dp = 16.dp),
                                color = if (currentRoute.title == sections.title) Primary500 else Gray500,
                                style = FanwooriTypography.body2,
                                maxLines = 1
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                }
            }

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.addHomeGraph(
    onItemSelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateBottomBar: (HomeSections) -> Unit,
    modifier: Modifier = Modifier,
    onVotingClick: (vote_id: Int, unlimitedTickets: Long, todayTickets: Long, star: VoteMainStar?, isMyStar: Boolean, isMission: Boolean, voteViewModel: VoteHomeViewModel) -> Unit,
    navController: NavController,
    lazyListState: HashMap<String, LazyListState>?,
    purchaseHelper: PurchaseHelper
) {
    composable(HomeSections.Vote.route) {
        VoteHome(
            onNavigateClick = { section ->
                onNavigateBottomBar(section)
            },
            navController = navController,
            modifier = modifier,
            onVotingClick = { voteId: Int, unlimitedTickets: Long, todayTickets: Long, star: VoteMainStar?, isMyStar: Boolean, isMission: Boolean, voteViewModel: VoteHomeViewModel ->
                onVotingClick(
                    voteId,
                    unlimitedTickets,
                    todayTickets,
                    star,
                    isMyStar,
                    isMission,
                    voteViewModel
                )
            },
            lazyListState = lazyListState?.get(HomeSections.Vote.route),
            purchaseHelper = purchaseHelper
        )
    }
    composable(HomeSections.MyPage.route) { from ->
        MyPageHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            onNavigateClick = { section ->
                onNavigateBottomBar(section)
            },
            modifier = modifier,
            navController = navController,
            purchaseHelper = purchaseHelper
        )
    }
    composable(HomeSections.Ranking.route) { from ->
        RankHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            navController = navController,
            onNavigateClick = { section ->
                onNavigateBottomBar(section)
            },
            modifier = modifier,
            lazyListState = lazyListState?.get(HomeSections.Ranking.route)
        )
    }
    composable(HomeSections.Charge.route) { from ->
        ChargeHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            navController = navController,
            onNavigateClick = { section ->
                onNavigateBottomBar(section)
            },
            modifier = modifier,
            purchaseHelper = purchaseHelper
        )
    }

}