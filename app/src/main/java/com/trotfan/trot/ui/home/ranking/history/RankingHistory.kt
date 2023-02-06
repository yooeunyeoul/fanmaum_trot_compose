package com.trotfan.trot.ui.home.ranking.history

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.ranking.history.component.RankingHistoryTab
import com.trotfan.trot.ui.home.ranking.history.component.daily.DailyCalenderPicker
import com.trotfan.trot.ui.home.ranking.history.component.monthly.MonthlyCalenderPicker
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RankingHistory(
    navController: NavController? = null,
    onVotingClick: () -> Unit,
    rankingHistoryViewModel: RankingHistoryViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var monthlyEmptyState by remember {
        mutableStateOf(false)
    }
    var dailyEmptyState by remember {
        mutableStateOf(false)
    }
    val bottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
    val coroutineScope = rememberCoroutineScope()

    Surface {
        ModalBottomSheetLayout(
            sheetContent = {
                if (selectedTabIndex == 0) {
                    MonthlyCalenderPicker(
                        modalBottomSheetState = bottomSheetState,
                        onConfirmClick = {
                            rankingHistoryViewModel.settingDateRange()
                            rankingHistoryViewModel.getMonthlyStarRankingList()
                        }
                    )
                } else {
                    DailyCalenderPicker(modalBottomSheetState = bottomSheetState,
                        onConfirmClick = {
                            rankingHistoryViewModel.getDailyStarRankingList()
                        })
                }
            },
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            BackHandler(enabled = bottomSheetState.isVisible) {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            }

            Column {
                CustomTopAppBar(title = "지난 순위", icon = R.drawable.icon_back) {
                    navController?.popBackStack()
                }

                RankingHistoryTab(selectedTabIndex) {
                    selectedTabIndex = it
                }

                if (selectedTabIndex == 0) {
                    RankingHistoryMonthly(
                        emptyState = monthlyEmptyState,
                        onCalenderClick = {
                            coroutineScope.launch {
                                val date = it.split("/")
                                rankingHistoryViewModel.monthlyYear.emit(date[0].toInt())
                                rankingHistoryViewModel.monthlyMonth.emit(date[1].toInt())
                                bottomSheetState.show()
                            }
                        },
                        onItemClick = { starRanking ->
                            navController?.navigate("${Route.RankingHistoryCumulative.route}/${starRanking.id}/${starRanking.name}/${rankingHistoryViewModel.monthlyYear.value}-${rankingHistoryViewModel.monthlyMonth.value}")
                        },
                        onVotingClick = onVotingClick,
                        navController = navController
                    )
                } else {
                    RankingHistoryDaily(
                        emptyState = dailyEmptyState,
                        onCalenderClick = {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        },
                        onVotingClick = onVotingClick,
                        navController = navController
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun RankingHistoryPreview() {
    FanwooriTheme {
        RankingHistory(
            onVotingClick = {}
        )
    }
}