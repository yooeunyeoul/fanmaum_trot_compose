package com.trotfan.trot.ui.home.charge.mission

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.RewardedVideoListener
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.mypage.setting.HyphenText
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary100
import com.trotfan.trot.ui.theme.Primary700
import com.trotfan.trot.ui.theme.Secondary50
import com.trotfan.trot.ui.theme.gradient04
import com.trotfan.trot.ui.theme.gradient05
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.textBrush

@Composable
fun VideoAd(
    navController: NavController?,
    count: Int? = 0
) {
    val configuration = LocalConfiguration.current
    val itemWidth = (configuration.screenWidthDp.dp - 56.dp) / 4
    var adCount by remember {
        mutableStateOf(0)
    }
    val scrollState = rememberScrollState()
    val infoList = listOf(
        "본 이벤트는 팬마음 회원 대상으로 진행되며, 팬마음 회원 로그인 및 전자금융거래 이용약관, 개인정보 수집 이용 동의 시에만 지급 및 사용이 가능합니다.",
        "동영상 광고는 하루에 20 번 볼 수 있습니다.",
        "동영상 광고 시청 시 투표권 300 장을 받을 수 있습니다.",
        "동영상을 끝까지 보지 않으면 투표권을 받을 수 없습니다.",
        "지급된 투표권은 당일 자정에 소멸되며, 소멸된 투표권은 복구되지 않습니다.",
        "지급된 투표권은 마이페이지 > 내 투표권에서 확인하실 수 있습니다.",
        "본 이벤트는 사정에 따라 변경되거나 조기종료될 수 있으며, 변경 및 종료에 관한 내용은 공지사항을 통해 안내됩니다.",
        "팬마음 정책에 어긋나거나 부정한 방법으로 이벤트 참여가 의심되는 경우, 투표권은 지급되지 않으며 지급된 투표권은 회수 처리됩니다.",
        "투표권 미지급 관련 문의는 마이페이지 > 문의하기를 통해 가능합니다."
    )

    IronSource.setRewardedVideoListener(object : RewardedVideoListener {
        override fun onRewardedVideoAdOpened() {
        }

        override fun onRewardedVideoAdClosed() {
        }

        override fun onRewardedVideoAvailabilityChanged(p0: Boolean) {
        }

        override fun onRewardedVideoAdStarted() {
        }

        override fun onRewardedVideoAdEnded() {
        }

        override fun onRewardedVideoAdRewarded(p0: Placement?) {
            adCount = adCount.plus(1)
        }

        override fun onRewardedVideoAdShowFailed(p0: IronSourceError?) {
        }

        override fun onRewardedVideoAdClicked(p0: Placement?) {
        }

    })

    adCount = 20 - (count ?: 0)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = gradient05),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
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
                        items(20) { index ->
                            if (index < adCount!!) {
                                CompleteButton(height = itemWidth)
                            } else if (index == adCount) {
                                PlayButton(height = itemWidth)
                            } else {
                                LockButton(height = itemWidth)
                            }
                        }
                    },
                    modifier = Modifier
                        .heightIn(configuration.screenWidthDp.dp, 1200.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    userScrollEnabled = false
                )
                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 32.dp)
            ) {
                Text(
                    text = "상세안내",
                    style = FanwooriTypography.subtitle3,
                    color = Gray700,
                    modifier = Modifier.padding(start = 24.dp)
                )
                for (i in infoList) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HyphenText(
                        first = "-",
                        text = i,
                        color = Gray700
                    )
                }
            }
        }
        AppbarMLeftIcon(
            title = "동영상 광고", icon = R.drawable.icon_back, modifier = Modifier.background(
                Secondary50
            ), onIconClick = {
                navController?.previousBackStackEntry?.savedStateHandle?.set("count", 20 - adCount)
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
            .clickable {
                if (IronSource.isRewardedVideoAvailable())
                    IronSource.showRewardedVideo()
            }
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

@Composable
fun CompleteButton(height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Gray500)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_check),
                tint = Gray700,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(text = "완료", style = FanwooriTypography.button1, color = Gray700)
        }
    }
}

@Composable
fun LockButton(height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Gray200)
    ) {
        Image(
            painter = painterResource(id = R.drawable.charge_lock),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun VideoAdPreview() {
    FanwooriTheme {
        VideoAd(null)
    }
}