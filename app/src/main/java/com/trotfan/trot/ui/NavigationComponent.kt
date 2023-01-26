package com.trotfan.trot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.model.Expired
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.home.ranking.history.RankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.cumulative.CumulativeRanking
import com.trotfan.trot.ui.home.vote.benefits.VoteBenefits
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.ui.signup.*
import com.trotfan.trot.ui.webview.PublicWebView

enum class Route(
    @StringRes val title: Int? = null,
    val route: String
) {
    Login(route = "login"),
    SearchStar(route = "searchStar"),
    SelectStar(route = "selectStar"),
    CertificationPhoneNumber(route = "certificationPhoneNumber"),
    SettingNickname(route = "settingNickName"),
    InvitationCode(route = "invitationCode"),
    VoteBenefits(route = "voteBenefits"),
    RankingHistory(route = "rankingHistory"),
    RankingHistoryCumulative(route = "RankingHistoryCumulative"),
    WebView(route = "WebView")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationComponent(
    navController: NavHostController,
    onVotingClick: (vote_id: Int, voteTicket: Expired, star: VoteMainStar?, viewModel: VoteHomeViewModel) -> Unit,
    onNavigateBottomBar: (HomeSections) -> Unit,
    lazyListStates: HashMap<String, LazyListState>,
    purchaseHelper: PurchaseHelper
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        composable(Route.Login.route) {
            LoginScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        addHomeGraph(
            onItemSelected = { _, _ ->
                // 각화면의 디테일 작업
            },
            onNavigateBottomBar = {
                onNavigateBottomBar.invoke(it)
            },
            onVotingClick = { voteId: Int, voteTicket: Expired, star: VoteMainStar?, viewModel: VoteHomeViewModel ->
                onVotingClick(voteId, voteTicket, star, viewModel)
            },
            navController = navController,
            lazyListState = lazyListStates,
            purchaseHelper = purchaseHelper
        )
        composable(Route.SelectStar.route) {
            SelectStarScreen(
                navController = navController,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
        composable(Route.SearchStar.route) {
            SearchStarScreen(
                navController = navController,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
        composable(Route.CertificationPhoneNumber.route) {
            CertificationPhoneScreen(
                navController = navController,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
        composable(Route.SettingNickname.route) {
            SettingNicknameScreen(
                navController = navController,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
        composable(Route.InvitationCode.route) {
            InvitationScreen(
                navController = navController,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
        composable(Route.VoteBenefits.route) {
            VoteBenefits(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(Route.RankingHistory.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                RankingHistory(
                    navController = navController,
                    onVotingClick = { onNavigateBottomBar.invoke(HomeSections.Vote) }
                )
            }
        }
        composable("${Route.WebView.route}/{uri}", arguments = listOf(
            navArgument(name = "uri") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            PublicWebView(
                backStackEntry.arguments?.getString("uri")
            )
        }
        composable("${Route.RankingHistoryCumulative.route}/{starId}/{starName}/{date}",
            arguments = listOf(
                navArgument(name = "starId") {
                    type = NavType.IntType
                },
                navArgument(name = "starName") {
                    type = NavType.StringType
                },
                navArgument(name = "date") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            CumulativeRanking(
                navController = navController,
                backStackEntry.arguments?.getInt("starId"),
                backStackEntry.arguments?.getString("starName"),
                backStackEntry.arguments?.getString("date")
            )
        }
    }
}