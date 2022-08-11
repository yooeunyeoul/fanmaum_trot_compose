package com.trotfan.trot.ui.login

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.ui.invitation.InvitationScreen
import com.trotfan.trot.viewmodel.AuthViewModel


enum class LoginNav(
    @StringRes val title: Int? = null,
    val route: String
) {
    Login(route = "login/login")
}


fun NavGraphBuilder.addLoginGrape(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(LoginNav.Login.route) { from ->
        LoginScreen(
            navController = navController,
            modifier = modifier
        )
    }
}