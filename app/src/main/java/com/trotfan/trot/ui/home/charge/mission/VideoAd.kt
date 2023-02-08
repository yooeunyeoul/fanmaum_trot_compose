package com.trotfan.trot.ui.home.charge.mission

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.textBrush

@Composable
fun VideoAd(navController: NavController?) {
    val configuration = LocalConfiguration.current
    val itemWidth = (configuration.screenWidthDp.dp - 56.dp) / 4
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = gradient05),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.charge_video_title),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "1회 시청 시 300투표권 x 20회 ",
                    style = FanwooriTypography.button1,
                    color = Gray800
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "매일 최대", style = FanwooriTypography.button1, color = Gray800)
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.icon_vote),
                        contentDescription = null
                    )
                    Text(
                        text = "6,000 투표권",
                        style = FanwooriTypography.subtitle4,
                        modifier = Modifier
                            .textBrush(
                                gradient04
                            )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "받으세요!", style = FanwooriTypography.button1, color = Gray800)
                }
                Spacer(modifier = Modifier.height(40.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(20) {
                            PlayButton(itemWidth)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    userScrollEnabled = false
                )
            }
        }
        AppbarMLeftIcon(
            title = "동영상 광고", icon = R.drawable.icon_back, modifier = Modifier.background(
                Secondary50
            ), onIconClick = {
                navController?.popBackStack()
            }
        )
    }
}

@Composable
fun PlayButton(height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .border(
                width = 3.dp,
                brush = gradient04,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Primary100)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.charge_videoplay),
                contentDescription = null
            )
            Text(text = "재생", style = FanwooriTypography.button1, color = Primary700)
        }
    }
}

@Preview
@Composable
fun VideoAdPreview() {
    FanwooriTheme {
        VideoAd(null)
    }
}