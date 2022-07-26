package com.trotfan.trot.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.ui.Destinations.DETAIL_ID_KEY
import com.trotfan.trot.ui.Destinations.HOME_ROUTE
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.TrotBottomBar
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope

object Destinations{
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val DETAIL_ID_KEY = "detailId"
}


@Composable
fun TrotApp(
    viewModel: MainViewModel = viewModel()
) {
    FanwooriTheme {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val navController: NavHostController = rememberNavController()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val testData by viewModel.testData.collectAsState()




        Scaffold(
            bottomBar = {
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
            },
            scaffoldState = scaffoldState
        ) { innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = HOME_ROUTE
            ) {
                navigation(
                    route = HOME_ROUTE,
                    startDestination = HomeSections.VOTE.route
                ) {
                    addHomeGraph(
                        onItemSelected = { id, entry ->
                            // 각화면의 디테일 작업
                        }
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