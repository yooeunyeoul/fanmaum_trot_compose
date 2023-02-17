package com.trotfan.trot.ui.home.mypage.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.clickableSingle
import java.net.URLEncoder

@Composable
fun MyPageList(navController: NavController?) {
    Surface(
        color = Color.White,
        elevation = 1.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            FriendInviteComponent(navController = navController)
            Line()
            MyPageListComponent(
                text = "공지사항",
                url = "https://mktfanmaum.notion.site/ece846d3b5e04b7589d07b8507f5f134",
                navController = navController
            )
            Line()
            MyPageListComponent(
                text = "이벤트",
                url = "https://mktfanmaum.notion.site/7df7c321d0714997894668426354f88a",
                navController = navController
            )
            Line()
            MyPageListComponent(
                text = "이용가이드",
                url = "https://mktfanmaum.notion.site/6abe9c2ead0d4fe6be976cc4d95154c7",
                navController = navController
            )
            Line()
            MyPageListComponent(
                text = "자주 묻는 질문",
                url = "https://mktfanmaum.notion.site/ad8444ca3f1940b9850dfea79d7f5128",
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(start = 24.dp, end = 24.dp)
            .background(Gray100)
    )
}

@Composable
fun FriendInviteComponent(navController: NavController?) {
    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .clickable {
                navController?.navigate(Route.FriendInvite.route)
            }) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp)
        ) {
            Row(verticalAlignment = CenterVertically) {
                Text(
                    text = "친구 초대",
                    style = FanwooriTypography.body3,
                    color = Gray750,
                    modifier = Modifier
                        .align(CenterVertically)
                )
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            Primary100
                        ),
                    contentAlignment = Center
                ) {
                    Text(
                        text = "500 투표권 적립",
                        style = FanwooriTypography.button2,
                        color = Primary800,
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "친구를 초대하여 투표권을 받아봐요!", style = FanwooriTypography.caption2, color = Gray650)
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray750,
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 24.dp)
        )
    }
}

@Composable
fun MyPageListComponent(text: String, url: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickableSingle {
                navController?.navigate("${Route.WebView.route}/${URLEncoder.encode(url)}")
            }
    ) {
        Text(
            text = text,
            style = FanwooriTypography.body3,
            color = Gray750,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 24.dp)
                .weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray750,
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 24.dp)
        )
    }
}