package com.trotfan.trot.ui.home

import android.os.Build
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.R
import com.trotfan.trot.datastore.dateManager
import com.trotfan.trot.model.Expired
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.home.charge.ChargeHome
import com.trotfan.trot.ui.home.dialog.AutoVotingDialog
import com.trotfan.trot.ui.home.dialog.FeverTimeDialog
import com.trotfan.trot.ui.home.dialog.RollingDialog
import com.trotfan.trot.ui.home.mypage.MyPageHome
import com.trotfan.trot.ui.home.ranking.RankHome
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.home.vote.VoteHome
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class HomeSections(
    val title: String,
    val route: String
) {
    Vote(title = "일일투표", route = "home/vote"),
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
    viewModel: HomeViewModel = hiltViewModel()
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
                            if (date.rollingDate != LocalDate.now().toString()) {
                                viewModel.rollingState.emit(true)
                            }
                        }
                        viewModel.autoVoteStatus.emit(true)
                        viewModel.feverStatus.emit(true)
                        viewModel.rollingState.emit(true)
                        viewModel.updateState.emit(false)
                    }
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
            LaunchedEffect(mainPopups?.layers) {
                context.dateManager.data.collect {
                    if (it.rollingDate == LocalDate.now().toString()) {
                        viewModel.rollingState.emit(false)
                    }
                }
            }

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
                                fontSize = 15.sp,
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
    onVotingClick: (vote_id: Int, vote_ticket: Expired, star: VoteMainStar?, voteViewModel: VoteHomeViewModel) -> Unit,
    navController: NavController,
    lazyListState: LazyListState
) {
    composable(HomeSections.Vote.route) {
        VoteHome(
            onNavigateClick = { section ->
                onNavigateBottomBar(section)
            },
            navController = navController,
            modifier = modifier,
            onVotingClick = { voteId: Int, voteTicket: Expired, star: VoteMainStar?, voteViewModel: VoteHomeViewModel ->
                onVotingClick(voteId, voteTicket, star, voteViewModel)
            },
            lazyListState = lazyListState
        )
    }
    composable(HomeSections.MyPage.route) { from ->
        MyPageHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.Ranking.route) { from ->
        RankHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
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
            modifier = modifier
        )
    }

}