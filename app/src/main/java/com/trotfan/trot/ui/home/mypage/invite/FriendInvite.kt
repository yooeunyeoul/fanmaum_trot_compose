package com.trotfan.trot.ui.home.mypage.invite

import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.mypage.setting.HyphenText
import com.trotfan.trot.ui.utils.clickable
import kotlinx.coroutines.delay

@Composable
fun FriendInvite(navController: NavController? = null) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val infoList = listOf(
        "친구 초대 이벤트는 팬마음 앱 내에서만 진행하며, 앱 로그인 후 참여할 수 있습니다",
        "추천인은 팬마음 앱 가입 단계에서만 입력할 수 있습니다.",
        "이미 가입한 친구는 초대할 수 없습니다.",
        "친구 초대 보상 투표권은 초대한 최대 20명에 대해서만 받을 수 있습니다.",
        "친구에게 전송한 초대 코드는 3일 동안만 유효하며, 이후에는 해당 코드를 사용할 수 없습니다. (친구가 3일 이내 코드를 사용하여 가입을 하지 않을 경우 3일 이후에는 초대코드 다시 전송해야 함)",
        "친구 초대 보상은 회원가입을 한 시점으로부터 24시간 이내 자동으로 지급됩니다. (24시간 이후에도 지급이 이루어지지 않는다면 문의하기를 통해 문의 드립니다.)",
        "친구 초대 보상은 내부 사정에 따라 예고 없이 내용이 변경되거나 종료될 수 있습니다.",
        "이벤트 관련 문의사항은 앱 내 문의하기를 이용 부탁드립니다."
    )
    var toastState by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AppbarMLeftIcon(
            title = "친구 초대",
            onIconClick = { navController?.popBackStack() },
            icon = R.drawable.icon_back
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                Text(text = "친구도 받고, 나도 받는", style = FanwooriTypography.subtitle5, color = Gray800)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                ) {
                    Text(text = "친구 초대 혜택 :", style = FanwooriTypography.subtitle5, color = Gray800)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "2,500 투표권",
                        style = FanwooriTypography.subtitle5,
                        color = Primary500
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "지금 바로 팬마음에 친구를 초대해보세요!",
                    style = FanwooriTypography.body3,
                    color = Gray650
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mypage_invitecode),
                        contentDescription = null
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Gray50),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "내 초대 코드", style = FanwooriTypography.body3, color = Gray850)
                        Icon(
                            painter = painterResource(id = R.drawable.icon_heartfilled),
                            contentDescription = null,
                            tint = Primary500,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "#Code", style = FanwooriTypography.h1, color = Gray900)
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray200)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_share_aos),
                                contentDescription = null,
                                tint = Gray750,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "공유하기", style = FanwooriTypography.button1, color = Gray750)
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(16.dp)
                                .background(Gray200)
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    toastState = true
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_copy),
                                contentDescription = null,
                                tint = Gray750,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "복사하기", style = FanwooriTypography.button1, color = Gray750)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "나의 친구 초대 현황",
                    style = FanwooriTypography.body2,
                    color = Gray650,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(82.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Gray50),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "초대한 친구", style = FanwooriTypography.body3, color = Gray700)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "0", style = FanwooriTypography.subtitle1, color = Gray900)
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(34.dp)
                            .background(Gray200)
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "총 적립 투표권", style = FanwooriTypography.body3, color = Gray700)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "0", style = FanwooriTypography.subtitle1, color = Gray900)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .background(Gray100)
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 32.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "유의사항",
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

        AnimatedVisibility(
            visible = toastState,
            modifier = Modifier.align(TopCenter),
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(52.dp)
                    .alpha(0.88f)
                    .clip(RoundedCornerShape(52.dp))
                    .background(Gray900)
                    .align(TopCenter),
                contentAlignment = Center,
            ) {
                Text(
                    text = "복사되었어요!",
                    style = FanwooriTypography.body3,
                    color = Color.White,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                )
            }
            LaunchedEffect(toastState) {
                delay(1000L)
                toastState = false
            }
        }
    }
}

@Preview
@Composable
fun FriendInvitePreview() {
    FanwooriTheme {
        FriendInvite()
    }
}