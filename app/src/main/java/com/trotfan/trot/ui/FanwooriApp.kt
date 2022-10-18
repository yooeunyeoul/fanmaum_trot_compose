package com.trotfan.trot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.trotfan.trot.ui.Destinations.DETAIL_ID_KEY
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.TrotBottomBar
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.home.vote.benefits.addVoteBenefitsGraph
import com.trotfan.trot.ui.home.vote.dialog.VotingBottomSheet
import com.trotfan.trot.ui.login.LoginNav
import com.trotfan.trot.ui.login.addLoginGraph
import com.trotfan.trot.ui.signup.SignUpSections
import com.trotfan.trot.ui.signup.addSignUpGraph
import com.trotfan.trot.ui.theme.FanwooriTheme
import kotlinx.coroutines.CoroutineScope

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val DETAIL_ID_KEY = "detailId"
}


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FanwooriApp(
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
        var votingCompleteState by remember {
            mutableStateOf(false)
        }



        ModalBottomSheetLayout(
            sheetContent = {
                VotingBottomSheet(votingBottomSheetState) {
                    votingCompleteState = true
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
            ) { innerPadding ->

                if (votingCompleteState) {
                    HorizontalDialog(
                        titleText = "스타이름에게 투표완료",
                        contentText = "스타이름 님에 대한\n" +
                                "나의 투표 기여도를 공유하고,\n" +
                                "더 많은 친구들과 응원해보세요!",
                        positiveText = "공유하기",
                        negativeText = "완료",
                        modifier = Modifier,
                        onDismiss = {
                            votingCompleteState = false
                        }
                    )
                }

                NavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    startDestination = "로그인"
                ) {

                    navigation(
                        route = "로그인",
//                    startDestination = LoginNav.Login.route
                        startDestination = LoginNav.Login.route
                    ) {
                        addLoginGraph(
                            modifier = Modifier,
                            navController = navController
                        )
                        addHomeGraph(
                            onItemSelected = { id, entry ->
                                // 각화면의 디테일 작업
                            },
                            votingBottomSheetState = votingBottomSheetState,
                            navController = navController
                        )
                        addSignUpGraph(
                            navController = navController,
                            modifier = Modifier.padding(
                                start = 24.dp,
                                end = 24.dp
                            )
                        )

                        addVoteBenefitsGraph(
                            modifier = Modifier,
                            navController = navController
                        )
                    }

                    // 디테일 화면 눌렀을 때 이동하는 뷰
                    composable(
                        route = "${Destinations.DETAIL_ROUTE}}/{${DETAIL_ID_KEY}}",
                        arguments = listOf(
                            element = navArgument(DETAIL_ID_KEY,
                                builder = {
                                    type = NavType.LongType
                                })
                        )
                    ) { navBackStackEntry ->
                        val arguments = requireNotNull(navBackStackEntry.arguments)
                        val detailId = arguments.getLong(DETAIL_ID_KEY)

                    }
                }


            }
        }
    }
}