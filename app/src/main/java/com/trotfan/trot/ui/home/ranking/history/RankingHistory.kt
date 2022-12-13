package com.trotfan.trot.ui.home.ranking.history

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.home.ranking.history.component.RankingHistoryTab
import com.trotfan.trot.ui.theme.FanwooriTheme

@Composable
fun RankingHistory(
    navController: NavController? = null
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var monthlyEmptyState by remember {
        mutableStateOf(false)
    }

    Column {
        CustomTopAppBar(title = "지난 순위", icon = R.drawable.icon_back) {
        }

        RankingHistoryTab(selectedTabIndex) {

            if (selectedTabIndex == it) {
                monthlyEmptyState = monthlyEmptyState.not()
            }
            selectedTabIndex = it
        }

        if (selectedTabIndex == 0) {
            RankingHistoryMonthly(monthlyEmptyState)
        } else {
            RankingHistoryDaily()
        }
    }
}

@Preview
@Composable
fun RankingHistoryPreview() {
    FanwooriTheme {
        RankingHistory()
    }
}