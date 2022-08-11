package com.trotfan.trot.ui.signup

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.ui.invitation.InvitationScreen


enum class SignUpSections(
    @StringRes val title: Int? = null,
    val route: String
) {
    SearchStar(route = "signUp/searchStar"),
    SelectStar(route = "signUp/selectStar"),
    CertificationPhoneNumber(route = "signUp/CertificationPhoneNumber"),
    SettingNickName(route = "signUp/SettingNickName"),
    InvitationCode(route = "signUp/InvitationCode")
}


fun NavGraphBuilder.addSignUpGraph(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable(SignUpSections.SelectStar.route) { from ->
        SelectStarScreen(
            navController = navController,
            modifier = modifier
        )
    }
    composable(SignUpSections.SearchStar.route) { from ->
        SearchStarScreen(
            navController = navController,
            modifier = modifier
        )
    }
    composable(SignUpSections.CertificationPhoneNumber.route) { from ->
        CertificationPhoneScreen(
            navController = navController,
            modifier = modifier
        )
    }
    composable(SignUpSections.SettingNickName.route) { from ->
        SettingNicknameScreen(
            navController = navController,
            modifier = modifier
        )
    }
    composable(SignUpSections.InvitationCode.route) { from ->
        InvitationScreen(
            navController = navController,
            modifier = modifier
        )
    }


}