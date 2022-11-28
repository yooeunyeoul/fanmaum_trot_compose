package com.trotfan.trot.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.TrotBottomBar
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.home.vote.dialog.VotingBottomSheet
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val DETAIL_ID_KEY = "detailId"
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FanwooriApp(
    viewModel: HomeViewModel = hiltViewModel()
) {
    FanwooriTheme {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val navController: NavHostController = rememberNavController()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val votingBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
        val votingCompleteState by viewModel.votingCompleteState.collectAsState()



        ModalBottomSheetLayout(
            sheetContent = {
                VotingBottomSheet(votingBottomSheetState) {
//                    votingCompleteState = true
                    viewModel.postVoteTicket()
                }
            },
            sheetState = votingBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Scaffold(
                bottomBar = {
                    when (navBackStackEntry?.destination?.route) {
                        HomeSections.Vote.route, HomeSections.Ranking.route,
                        HomeSections.MyPage.route, HomeSections.Charge.route -> {
                            TrotBottomBar(
                                tabs = HomeSections.values(),
                                currentRoute = HomeSections.Vote.route,
                                onSelected = { route ->
                                    navController.navigate(route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(HomeSections.Vote.route) {
                                            saveState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
                scaffoldState = scaffoldState
            ) {
                BackHandler(enabled = votingBottomSheetState.isVisible) {
                    coroutineScope.launch {
                        votingBottomSheetState.hide()
                    }
                }
                NavigationComponent(
                    navController = navController,
                    onVotingClick = {
                        coroutineScope.launch {
                            votingBottomSheetState.show()
                        }
                    }
                )

                if (votingCompleteState == 1) {
                    coroutineScope.launch {
                        votingBottomSheetState.hide()
                    }
                    HorizontalDialog(
                        titleText = "스타이름에게 투표완료",
                        contentText = "스타이름 님에 대한\n" +
                                "나의 투표 기여도를 공유하고,\n" +
                                "더 많은 친구들과 응원해보세요!",
                        positiveText = "공유하기",
                        negativeText = "완료",
                        modifier = Modifier,
                        onDismiss = {
                            coroutineScope.launch {
                                viewModel.votingCompleteState.emit(0)
                            }
                        }
                    )
                }
            }
        }
    }
}