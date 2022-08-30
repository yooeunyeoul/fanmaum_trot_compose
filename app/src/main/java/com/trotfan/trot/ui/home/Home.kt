package com.trotfan.trot.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.charge.ChargeHome
import com.trotfan.trot.ui.home.mypage.MyPageHome
import com.trotfan.trot.ui.home.ranking.RankHome
import com.trotfan.trot.ui.home.vote.VoteHome
import com.trotfan.trot.ui.theme.*

enum class HomeSections(
    val title: String,
    val route: String
) {
    VOTE(title = "투표", route = "home/vote"),
    Ranking(title = "랭킹", route = "home/ranking"),
    Store(title = "스토어", route = "home/store"),
    MyPage(title = "마이페이지", route = "home/mypage")
}

private val BottomNavHeight = 56.dp
private val TextIconSpacing = 2.dp

@Preview
@Composable
fun PreviewTrotBottomBar() {
    FanwooriTheme {
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
    onSelected: (String) -> Unit
) {

    var selectedSection by remember {
        mutableStateOf(HomeSections.VOTE)
    }


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
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.White)
                        .fillMaxHeight()
                        .selectable(
                            selected = selectedSection.title == sections.title,
                            onClick = {
                                selectedSection = sections
                                onSelected(sections.route)
                            }
                        )
                ) {
                    Divider(thickness = 1.dp, color = Gray100)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .layoutId("icon")
                            .padding(horizontal = TextIconSpacing),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.iconback_default),
                                modifier = Modifier.height(18.3.dp),
                                contentDescription = null
                            )
                        }

                    )
                    Spacer(modifier = Modifier.height(2.9.dp))
                    Box(
                        modifier = Modifier
                            .layoutId("text")
                            .padding(horizontal = TextIconSpacing),
                        content = {
                            Text(
                                text = sections.title,
                                fontSize = 13.sp,
                                color = if (selectedSection.title == sections.title) Gray800 else Gray500,
                                style = FanwooriTypography.button2,
                                maxLines = 1
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


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
    composable(HomeSections.MyPage.route) { from ->
        RankHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.Ranking.route) { from ->
        ChargeHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }
    composable(HomeSections.Store.route) { from ->
        MyPageHome(
            onItemClick = { id ->
                onItemSelected(id, from)
            },
            modifier = modifier
        )
    }

}