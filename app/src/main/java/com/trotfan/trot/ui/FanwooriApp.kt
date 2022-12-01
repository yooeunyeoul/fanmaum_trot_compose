package com.trotfan.trot.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.TrotBottomBar
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.home.vote.dialog.VotingBottomSheet
import com.trotfan.trot.ui.home.vote.voteTopShareText
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
        val star: VoteMainStar? by viewModel.voteStar.collectAsState()
        val voteId: Int by viewModel.voteId.collectAsState()
        val votingCompleteState by viewModel.votingCompleteState.collectAsState()
        val context = LocalContext.current

        ModalBottomSheetLayout(
            sheetContent = {
                VotingBottomSheet(votingBottomSheetState) { star_id: Int?, quantity: Long? ->
                    star_id?.let {
                        viewModel.postVoteTicket(voteId = voteId, star_id, quantity!!)
                    }
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
                    onVotingClick = { voteId: Int, star: VoteMainStar? ->
                        coroutineScope.launch {
                            star?.let {
                                viewModel.voteStar.emit(star)
                                viewModel.voteId.emit(voteId)
                                viewModel.voteCnt.emit(TextFieldValue(""))
                                votingBottomSheetState.show()
                            }
                        }
                    }
                )

                when (votingCompleteState) {
                    1 -> {
                        coroutineScope.launch {
                            votingBottomSheetState.hide()
                        }
                        HorizontalDialog(
                            titleText = "${star?.name}에게 투표완료",
                            contentText = "${star?.name} 님에 대한\n" +
                                    "나의 투표 기여도를 공유하고,\n" +
                                    "더 많은 친구들과 응원해보세요!",
                            positiveText = "공유하기",
                            negativeText = "완료",
                            modifier = Modifier,
                            onPositive = {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT, voteTopShareText(star?.name)
                                    )

                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            },
                            onDismiss = {
                                coroutineScope.launch {
                                    viewModel.votingCompleteState.emit(0)
                                }
                            }
                        )
                    }
                    909 -> {
                        coroutineScope.launch {
                            votingBottomSheetState.hide()
                        }
                        VerticalDialog(
                            contentText = "지금은 집계 시간이에요!\n" +
                                    "집계 중에는 투표할 수 없어요.\n" +
                                    "23:30:00 ~ 23:59:59", buttonOneText = "확인"
                        ) {
                            coroutineScope.launch {
                                viewModel.votingCompleteState.emit(0)
                            }
                        }
                    }
                    44 -> {
                        VerticalDialog(
                            contentText = "네트워크 오류로\n" +
                                    "투표가 완료되지 않았습니다.\n" +
                                    "잠시 후 다시 시도해주세요.", buttonOneText = "확인"
                        ) {
                            coroutineScope.launch {
                                viewModel.votingCompleteState.emit(0)
                            }
                        }
                    }
                }
            }
        }
    }
}