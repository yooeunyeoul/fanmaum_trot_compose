package com.trotfan.trot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.home.charge.mission.AttendanceCheck
import com.trotfan.trot.ui.home.charge.mission.TodayMission
import com.trotfan.trot.ui.home.charge.mission.VideoAd
import com.trotfan.trot.ui.home.charge.mission.luckyRoulette
import com.trotfan.trot.ui.home.mypage.invite.FriendInvite
import com.trotfan.trot.ui.home.mypage.modify.ProfileModify
import com.trotfan.trot.ui.home.mypage.setting.*
import com.trotfan.trot.ui.home.mypage.votehistory.MyVoteHistory
import com.trotfan.trot.ui.home.ranking.history.RankingHistory
import com.trotfan.trot.ui.home.ranking.history.component.cumulative.CumulativeRanking
import com.trotfan.trot.ui.home.vote.benefits.VoteBenefits
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.ui.permission.PermissionAgreement
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
    SettingSecession(route = "SettingSecession"),
    TodayMission(route = "TodayMission"),
    AttendanceCheck(route = "AttendanceCheck"),
    VideoAd(route = "VideoAd"),
    LuckyRoulette(route = "luckyRoulette"),
    FriendInvite(route = "FriendInvite"),
    AppInfo(route = "AppInfo")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationComponent(
    navController: NavHostController,
    onVotingClick: (vote_id: Int, unlimitedTickets: Long, todayTickets: Long, star: VoteMainStar?, isMyStar: Boolean, isMission: Boolean, voteViewModel: VoteHomeViewModel) -> Unit,
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
            onVotingClick = { voteId: Int, unlimitedTickets: Long, todayTickets: Long, star: VoteMainStar?, isMyStar: Boolean, isMission: Boolean, voteViewModel: VoteHomeViewModel ->
                onVotingClick(voteId, unlimitedTickets, todayTickets, star, isMyStar, isMission, voteViewModel)
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
            RankingHistory(
                navController = navController,
                onVotingClick = { onNavigateBottomBar.invoke(HomeSections.Vote) }
            )
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
                purchaseHelper = purchaseHelper
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
            "${Route.PermissionAgreement.route}/{step}",
            arguments = listOf(
                navArgument(name = "step") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            PermissionAgreement(
                navController = navController,
                backStackEntry.arguments?.getString("step")
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
        composable(Route.TodayMission.route) {
            TodayMission(
                navController = navController
            )
        }
        composable(Route.AttendanceCheck.route) {
            AttendanceCheck(
                navController = navController
            )
        }
        composable("${Route.VideoAd.route}/{count}", arguments = listOf(
            navArgument(name = "count") {
                type = NavType.IntType
            }
        )) { backStackEntry ->
            VideoAd(
                navController = navController,
                backStackEntry.arguments?.getInt("count")
            )
        }
        composable(Route.LuckyRoulette.route) {
            luckyRoulette(
                navController = navController
            )
        }
        composable(Route.FriendInvite.route) {
            FriendInvite(
                navController = navController
            )
        }
        composable(Route.AppInfo.route) {
            AppInfo(navController = navController)
        }
    }
}