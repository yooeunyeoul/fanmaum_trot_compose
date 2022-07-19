package com.trotfan.trot.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.charge.ChargeHome
import com.trotfan.trot.ui.home.mypage.MyPageHome
import com.trotfan.trot.ui.home.ranking.RankHome
import com.trotfan.trot.ui.home.vote.VoteHome
import com.trotfan.trot.ui.theme.TrotTheme

enum class HomeSections(
    @StringRes val title: Int,
    val route: String
) {
    VOTE(title = R.string.home_vote, route = "home/vote"),
    CHARGE(title = R.string.home_charge, route = "home/charge"),
    MyProfile(title = R.string.home_mypage, route = "home/myProfile"),
    RANKING(title = R.string.home_ranking, route = "home/ranking")
}

private val BottomNavHeight = 56.dp
private val TextIconSpacing = 2.dp

@Preview
@Composable
fun PreviewTrotBottomBar() {
    TrotTheme {
        TrotBottomBar(
            tabs = HomeSections.values(),
            currentRoute = HomeSections.VOTE.route,
            onSelected = {

            }
        )
    }
}

@Composable
fun TrotBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    selected: Boolean = false,
    onSelected: (String) -> Unit
) {
    val routes = remember {
        tabs.map { it.route }
    }
    val currentSections = tabs.first { it.route == currentRoute }

    Surface(
        color = Color.White,
        contentColor = Color.Blue,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.height(BottomNavHeight),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { sections ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.DarkGray)
                        .fillMaxHeight()
                        .selectable(
                            selected = selected,
                            onClick = { onSelected(sections.route) }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .layoutId("icon")
                            .padding(horizontal = TextIconSpacing),
                        content = {
//                            Icon(
//                                painterResource(id = androidx.appcompat.R.drawable.abc_btn_check_material),
//                                contentDescription = null
//                            )
                        }

                    )
                    Box(
                        modifier = Modifier
                            .layoutId("text")
                            .padding(horizontal = TextIconSpacing),
                        content = {
                            Text(
                                text = stringResource(id = sections.title),
                                color = Color.White,
                                style = MaterialTheme.typography.button,
                                maxLines = 1
                            )
                        }
                    )


                }
            }

        }

    }
}

fun NavGraphBuilder.addHomeGraph(
    onItemSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.VOTE.route) { from ->
        VoteHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.RANKING.route) { from ->
        RankHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.CHARGE.route) { from ->
        ChargeHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.MyProfile.route) { from ->
        MyPageHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }

}