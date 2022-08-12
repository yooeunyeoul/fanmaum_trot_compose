package com.trotfan.trot.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.ui.Destinations.DETAIL_ID_KEY
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.TrotBottomBar
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.login.LoginNav
import com.trotfan.trot.ui.login.addLoginGrape
import com.trotfan.trot.ui.signup.addSignUpGraph
import com.trotfan.trot.ui.theme.FanwooriTheme
import kotlinx.coroutines.CoroutineScope

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val DETAIL_ID_KEY = "detailId"
}


@Composable
fun FanwooriApp(
) {
    FanwooriTheme {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val navController: NavHostController = rememberNavController()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        Scaffold(
            bottomBar = {
                when (navBackStackEntry?.destination?.route) {
                    HomeSections.VOTE.route, HomeSections.CHARGE.route,
                    HomeSections.RANKING.route, HomeSections.MyProfile.route -> {
                        TrotBottomBar(
                            tabs = HomeSections.values(),
                            currentRoute = HomeSections.VOTE.route,
                            onSelected = { route ->
                                navController.navigate(route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.findStartDestination().id) {
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

            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = "로그인"
            ) {

                navigation(
                    route = "로그인",
                    startDestination = LoginNav.Login.route
                ) {
                    addLoginGrape(
                        modifier = Modifier,
                        navController = navController
                    )
                    addHomeGraph(
                        onItemSelected = { id, entry ->
                            // 각화면의 디테일 작업
                        }
                    )
                    addSignUpGraph(
                        navController = navController,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp
                        )
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