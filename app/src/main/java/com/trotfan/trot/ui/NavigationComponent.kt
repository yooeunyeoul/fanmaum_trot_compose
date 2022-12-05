package com.trotfan.trot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trotfan.trot.model.Expired
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.model.VoteTicket
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.addHomeGraph
import com.trotfan.trot.ui.home.vote.benefits.VoteBenefits
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.ui.signup.*

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
    VoteBenefits(route = "voteBenefits")
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationComponent(
    navController: NavHostController,
    onVotingClick: (vote_id: Int, voteTicket: Expired, star: VoteMainStar?) -> Unit
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
            onVotingClick = { voteId: Int, voteTicket: Expired, star: VoteMainStar? ->
                onVotingClick(voteId, voteTicket, star)
            },
            navController = navController
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
    }
}