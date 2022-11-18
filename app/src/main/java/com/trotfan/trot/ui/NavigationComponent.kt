package com.trotfan.trot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    onVotingClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = HomeSections.Vote.route
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
            onVotingClick = {
                onVotingClick()
            },
            navController = navController
        )
        composable(Route.SelectStar.route) {
            SelectStarScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(Route.SearchStar.route) {
            SearchStarScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(Route.CertificationPhoneNumber.route) {
            CertificationPhoneScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(Route.SettingNickname.route) {
            SettingNicknameScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(Route.InvitationCode.route) {
            InvitationScreen(
                navController = navController,
                modifier = Modifier
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