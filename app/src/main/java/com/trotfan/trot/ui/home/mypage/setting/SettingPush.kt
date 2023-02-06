package com.trotfan.trot.ui.home.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*

@Composable
fun SettingPush(
    navController: NavController? = null,
    viewModel: SettingViewModel = hiltViewModel()
) {

    val dayAlarm by viewModel.dayEvent.collectAsState()
    val nightAlarm by viewModel.nightEvent.collectAsState()
    val freeTicketsAlarm by viewModel.freeVotes.collectAsState()
    val newVotes by viewModel.newVotes.collectAsState()
    val timeEvent by viewModel.timeEvent.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Column {
            AppbarMLeftIcon(
                title = "앱 알림 설정",
                icon = R.drawable.icon_back,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    ),
                onIconClick = {
                    navController?.popBackStack()
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            PushHead(
                title = "주간 광고, 이벤트 정보 수신 동의",
                content = "팬마음이 제공하는 이벤트 소식 알림을 받습니다.",
                isChecked = dayAlarm,
                onChecked = {
                    viewModel.setPushSetting(AlarmType.day_alarm, checked = it)
                }
            )
            PushBody(
                title = "타임이벤트 알림",
                content = "접속하기만 해도 무료 투표권을 드리는 타임이벤트 알림을 받습니다.",
                isChecked = timeEvent,
                onChecked = {
                    viewModel.setPushSetting(AlarmType.time_event, checked = it)
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            PushHead(
                title = "야간 광고, 이벤트 정보 수신 동의",
                content = "야간(오후 9시 ~ 오전 8시)에도 이벤트 소식 알림을 받습니다.",
                isChecked = nightAlarm,
                onChecked = {
                    viewModel.setPushSetting(AlarmType.night_alarm, checked = it)
                }
            )

            PushBody(
                title = "무료투표권 소멸 알림",
                content = "당일 소멸 예정인 무료투표권이 남았을 때 알림을 받습니다",
                isChecked = freeTicketsAlarm,
                onChecked = {
                    viewModel.setPushSetting(AlarmType.free_tickets_gone, checked = it)
                }
            )
            PushBody(title = "새 투표 알림", content = "새로운 투표가 올라오면 알림을 받습니다", isChecked = newVotes,
                onChecked = {
                    viewModel.setPushSetting(AlarmType.new_votes, checked = it)
                })
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(8.dp)
//            )
//            PushHead(
//                title = "타임이벤트 알림",
//                content = "접속하기만 해도 무료 투표권을 드리는 타임이벤트 알림을 받습니다.",
//                isChecked = timeEvent,
//                onChecked = {
//                    viewModel.setPushSetting(AlarmType.time_event, checked = it)
//                }
//            )
        }
    }
}

@Composable
fun PushHead(title: String, content: String, isChecked: Boolean, onChecked: (Boolean) -> (Unit)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, end = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = FanwooriTypography.body3, color = Gray800)
            Text(text = content, style = FanwooriTypography.caption1, color = Gray650)
        }
        Switch(checked = isChecked, onCheckedChange = {
            onChecked.invoke(it)
        }, modifier = Modifier.align(CenterVertically))

    }
}

@Composable
fun PushBody(title: String, content: String, isChecked: Boolean, onChecked: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray50)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, style = FanwooriTypography.body3, color = Gray800)
                Text(text = content, style = FanwooriTypography.caption1, color = Gray650)
            }
            Switch(checked = isChecked, onCheckedChange = {
                onChecked.invoke(it)
            }, modifier = Modifier.align(CenterVertically))

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Gray100
                )
        )
    }
}

@Preview
@Composable
fun SettingPushPreview() {
    FanwooriTheme {
        SettingPush()
    }
}