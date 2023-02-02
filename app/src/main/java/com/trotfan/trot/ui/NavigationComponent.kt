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
import com.trotfan.trot.ui.home.mypage.modify.ProfileModify
import com.trotfan.trot.ui.home.mypage.setting.Setting
import com.trotfan.trot.ui.home.mypage.setting.SettingAccount
import com.trotfan.trot.ui.home.mypage.setting.SettingPush
import com.trotfan.trot.ui.home.mypage.setting.SettingSecession
import com.trotfan.trot.ui.home.mypage.votehistory.MyVoteHistory
import com.trotfan.trot.ui.home.ranking.history.RankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.cumulative.CumulativeRanking
import com.trotfan.trot.ui.home.vote.benefits.VoteBenefits
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.ui.permission.PermissionAgreement
import com.trotfan.trot.ui.signup.TermsAgreement
import com.trotfan.trot.ui.signup.*
import com.trotfan.trot.ui.webview.PublicWebView

enum class Route(
    @StringRes val title: Int? = null,
    val route: String
) {
    Login(route = "login"),
    PermissionAgreement(route = "permissionAgreement"),
    TermsAgreement(route = "termsAgreement"),
    SearchStar(route = "searchStar"),
    SelectStar(route = "selectStar"),
    CertificationPhoneNumber(route = "certificationPhoneNumber"),
    SettingNickname(route = "settingNickName"),
    InvitationCode(route = "invitationCode"),
    VoteBenefits(route = "voteBenefits"),
    RankingHistory(route = "rankingHistory"),
    RankingHistoryCumulative(route = "rankingHistoryCumulative"),
    WebView(route = "webView"),
    MyProfileModify(route = "myProfileModify"),
    MyVoteHistory(route = "myVoteHistory"),
    Setting(route = "setting"),
    SettingAccount(route = "settingAccount"),
    SettingPush(route = "settingPush"),
    SettingSecession(route = "SettingSecession")
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
        startDestination = Route.SelectStar.route
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
                navController = navController,
                uri = backStackEntry.arguments?.getString("uri")
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
        composable(Route.MyProfileModify.route) {
            ProfileModify(
                navController = navController,
                logoutClick = { onNavigateBottomBar.invoke(HomeSections.Vote) }
            )
        }
        composable(Route.MyVoteHistory.route) {
            MyVoteHistory(
                navController = navController,
                onChargeClick = { onNavigateBottomBar.invoke(HomeSections.Charge) },
                purchaseHelper= purchaseHelper
            )
        }
        composable(Route.Setting.route) {
            Setting(
                navController = navController
            )
        }
        composable(Route.SettingAccount.route) {
            SettingAccount(
                navController = navController
            )
        }
        composable(Route.SettingPush.route) {
            SettingPush(
                navController = navController
            )
        }
        composable(Route.SettingSecession.route) {
            SettingSecession(
                navController = navController,
                signOutClick = { onNavigateBottomBar.invoke(HomeSections.Vote) }
            )
        }
        composable(
            "${Route.PermissionAgreement.route}/{terms}",
            arguments = listOf(
                navArgument(name = "terms") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            PermissionAgreement(
                navController = navController,
                backStackEntry.arguments?.getBoolean("terms")
            )
        }
        composable(Route.TermsAgreement.route) {
            TermsAgreement(
                navController = navController,
                onConfirmClick = {
                    navController.navigate(Route.SelectStar.route) {
                        popUpTo(Route.TermsAgreement.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}