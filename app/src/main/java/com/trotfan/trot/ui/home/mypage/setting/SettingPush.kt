package com.trotfan.trot.ui.home.mypage.setting

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnOutlineSecondaryLeftIcon
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.observeAsState
import kotlinx.coroutines.delay


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
    val finishState by viewModel.finishState.collectAsState()
    val context = LocalContext.current
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()
    var toastState by remember {
        mutableStateOf(false)
    }
    val toastText by viewModel.toastText.collectAsState()

    BackHandler(!finishState) {
        viewModel.setPushSetting()
    }

    LaunchedEffect(key1 = finishState, block = {
        if (finishState) {
            navController?.popBackStack()
        }
    })


    Box() {
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
                        if (!finishState) {
                            viewModel.setPushSetting()
                        }
                    }
                )

                val state = lifecycleState // 이게 있어야 뷰 onResume 에서 갱신함
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    AlarmScreen(
                        viewModel,
                        dayAlarm,
                        timeEvent,
                        nightAlarm,
                        freeTicketsAlarm,
                        newVotes,
                        onClick = {
                            toastState = true
                        }

                    )
                } else {
                    RequireAlarmOn(context)
                }
            }


        }
        AnimatedVisibility(
            visible = toastState,
            modifier = Modifier.align(Alignment.TopCenter),
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
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = toastText,
                    style = FanwooriTypography.body3,
                    color = Color.White,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
            LaunchedEffect(toastState) {
                delay(1000L)
                toastState = false
            }
        }

    }
}

@Composable
fun AlarmScreen(
    viewModel: SettingViewModel,
    dayAlarm: Boolean,
    timeEvent: Boolean,
    nightAlarm: Boolean,
    freeTicketsAlarm: Boolean,
    newVotes: Boolean,
    onClick: () -> Unit
) {
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
            viewModel.changeAlarmSetting(AlarmType.day_alarm, checked = it)
            onClick.invoke()
        }
    )
    PushBody(
        title = "타임이벤트 알림",
        content = "접속하기만 해도 무료 투표권을 드리는 타임이벤트 알림을 받습니다.",
        isChecked = timeEvent,
        onChecked = {
            viewModel.changeAlarmSetting(AlarmType.time_event, checked = it)
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
            viewModel.changeAlarmSetting(AlarmType.night_alarm, checked = it)
            onClick.invoke()
        }
    )

    PushBody(
        title = "무료투표권 소멸 알림",
        content = "당일 소멸 예정인 무료투표권이 남았을 때 알림을 받습니다",
        isChecked = freeTicketsAlarm,
        onChecked = {
            viewModel.changeAlarmSetting(AlarmType.free_tickets_gone, checked = it)
        }
    )
    PushBody(title = "새 투표 알림", content = "새로운 투표가 올라오면 알림을 받습니다", isChecked = newVotes,
        onChecked = {
            viewModel.changeAlarmSetting(AlarmType.new_votes, checked = it)
        })
}

@Composable
fun ColumnScope.RequireAlarmOn(context: Context) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "알림을 설정하세요", style = FanwooriTypography.subtitle1, color = Gray800)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Push 알림을 설정하지 않으면\n" +
                    "중요한 업데이트 내용, 이벤트 등\n" +
                    "꼭 필요한 정보를 받아볼 수 없어요.\n" +
                    "\n" +
                    "지금 알림을 설정해 보세요!",
            style = FanwooriTypography.body5,
            color = Gray700,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        BtnOutlineSecondaryLeftIcon(
            text = "알림 설정하기",
            onClick = {

//                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).run {
//                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                    data = Uri.fromParts("package", `package`, null)
//                    context.startActivity(this)
//                }
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            },
            icon = R.drawable.icon_bell
        )
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