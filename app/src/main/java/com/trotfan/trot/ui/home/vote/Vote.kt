package com.trotfan.trot.ui.home.vote

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.ui.components.navigation.CustomTopAppBarWithIcon
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*

@Composable
fun VoteHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CustomTopAppBarWithIcon(
            title = "투표",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Primary50)
                .height(76.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
        ) {
            voteToStar(starName = "임영웅", icon = "sladfj", userName = "양파쿵야")
//            voteIng()
//            voteEndHeader()
        }
        voteIngFooter()

        Spacer(modifier = modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .background(color = Gray100, shape = RoundedCornerShape(12.dp))
                        .padding(start = 16.dp, end = 16.dp)
                        .height(56.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.icon_add),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "우승혜택보기", modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_arrow),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(width = 1.dp, color = Gray200, shape = RoundedCornerShape(20.dp))
                        .height(40.dp)
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.3.dp))
                    Text(
                        text = "팬우리 투표안내",
                        fontSize = 15.sp,
                        style = FanwooriTypography.body2,
                        color = Gray700
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
                Divider(color = Gray100, modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .height(48.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "일일투표 실시간 차트",
                        style = FanwooriTypography.subtitle3,
                        color = Gray700,
                        fontSize = 15.sp
                    )
                }
            }
        }

    }
}

@Composable
fun voteToStar(icon: String, userName: String, starName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.icon_add), contentDescription = null)

        Text(
            text = "${userName}님이 ${starName}님에게 투표했어요!",
            maxLines = 1,
            style = FanwooriTypography.body2,
            fontSize = 15.sp,
            color = Secondary900
        )
    }
    Spacer(modifier = Modifier.height(3.dp))
    Row(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "100,000,000,000,000,000,000,000,000,00,00,00,00",
            color = Secondary500,

            modifier = Modifier.weight(weight = 1f, fill = false),
            style = FanwooriTypography.button1,
            maxLines = 1,
            fontSize = 17.sp,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "표",
            color = Secondary500,
            style = FanwooriTypography.button1,
            maxLines = 1,
            fontSize = 17.sp,
            overflow = TextOverflow.Visible
        )


    }
}

@Composable
fun voteIng() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
    ) {

        Text(
            text = "투표 진행 중",
            maxLines = 1,
            style = FanwooriTypography.body2,
            fontSize = 15.sp,
            color = Secondary900
        )
    }
    Spacer(modifier = Modifier.height(3.dp))
    Row(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "지금 내 스타에게 투표해보세요",
            color = Secondary500,

            modifier = Modifier.weight(weight = 1f, fill = false),
            style = FanwooriTypography.button1,
            maxLines = 1,
            fontSize = 17.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun voteEndHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
    ) {

        Text(
            text = "투표 마감",
            maxLines = 1,
            style = FanwooriTypography.body2,
            fontSize = 15.sp,
            color = Secondary900
        )
    }
    Spacer(modifier = Modifier.height(3.dp))
    Row(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "[투표 집계 시간] 23:30:00 ~ 23:59:59",
            color = Secondary500,

            modifier = Modifier.weight(weight = 1f, fill = false),
            style = FanwooriTypography.button1,
            maxLines = 1,
            fontSize = 17.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun voteIngFooter() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Primary50)
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Text(
            text = "투표마감 22.08.31",
            fontSize = 15.sp,
            color = Primary900,
            style = FanwooriTypography.caption1
        )
        Spacer(modifier = Modifier.width(10.33.dp))
        Icon(
            tint = Primary600,
            painter = painterResource(id = R.drawable.icon_timer),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.33.dp))
        Text(
            text = "23:30",
            fontSize = 15.sp,
            color = Primary900,
            style = FanwooriTypography.caption1
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "21일 남았어요",
            fontSize = 18.sp,
            color = Primary700,
            style = FanwooriTypography.subtitle2
        )


    }

}

@Composable
fun voteEndFooter() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Primary50)
            .fillMaxWidth()
            .height(46.dp)
    ) {
        Text(
            text = "자정부터 다시 투표할 수 있어요!",
            fontSize = 18.sp,
            color = Primary700,
            style = FanwooriTypography.subtitle2
        )


    }
}

