package com.trotfan.trot.ui.home.vote.benefits

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


enum class VoteBenefitsNav(
    @StringRes val title: Int? = null,
    val route: String
) {
    VoteBenefits(route = "vote/benefits")
}


fun NavGraphBuilder.addVoteBenefitsGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(VoteBenefitsNav.VoteBenefits.route) { from ->
        VoteBenefits(
            navController = navController,
            modifier = modifier
        )
    }
}