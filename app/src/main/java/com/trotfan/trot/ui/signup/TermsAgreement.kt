package com.trotfan.trot.ui.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.messaging.FirebaseMessaging
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.checkbox.CheckBoxNullText
import com.trotfan.trot.ui.home.mypage.setting.AlarmType
import com.trotfan.trot.ui.signup.viewmodel.TermsViewModel
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray50
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray750
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Gray900

@Composable
fun TermsAgreement(
    navController: NavController? = null,
    onConfirmClick: () -> Unit,
    viewModel: TermsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var allCheck by remember {
        mutableStateOf(false)
    }
    var childrenCheck by remember {
        mutableStateOf(false)
    }
    var termsOfUseCheck by remember {
        mutableStateOf(false)
    }
    var privacyCollectionCheck by remember {
        mutableStateOf(false)
    }
    var privacyUseCheck by remember {
        mutableStateOf(false)
    }
    var dayTimeAdsCheck by remember {
        mutableStateOf(false)
    }
    var nightAdsCheck by remember {
        mutableStateOf(false)
    }
    val apiResult by viewModel.apiResultState.collectAsState()

    LaunchedEffect(apiResult) {
        if (apiResult) {
            onConfirmClick()
        }
    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                    androidx.compose.material3.Text(
                        text = "이용약관 동의 안내",
                        style = FanwooriTypography.h1,
                        color = Gray900
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    androidx.compose.material3.Text(
                        text = "팬마음 서비스 이용을 위해\n" +
                                "약관에 동의해주세요",
                        style = FanwooriTypography.caption1,
                        color = Gray700
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Gray50)
                    ) {
                        Text(
                            text = "모두 확인, 동의합니다.",
                            style = FanwooriTypography.body3,
                            color = Gray800,
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(start = 16.dp)
                                .weight(1f)
                        )

                        CheckBoxNullText(
                            isChecked = allCheck, onCheckedChange = {
                                allCheck = allCheck.not()
                                childrenCheck = allCheck
                                termsOfUseCheck = allCheck
                                privacyCollectionCheck = allCheck
                                privacyUseCheck = allCheck
                                dayTimeAdsCheck = allCheck
                                nightAdsCheck = allCheck
                            }, modifier = Modifier
                                .align(CenterVertically)
                                .padding(end = 16.dp)
                        )
                    }

                    allCheck =
                        childrenCheck && termsOfUseCheck && privacyCollectionCheck && privacyUseCheck && dayTimeAdsCheck && nightAdsCheck

                    Spacer(modifier = Modifier.height(16.dp))
                    AgreeItem(
                        text = "(필수) 만 14세 이상입니다.",
                        isChecked = childrenCheck,
                        onCheckedChange = {
                            childrenCheck = childrenCheck.not()
                        }
                    )
                    AgreeItem(
                        text = "(필수) 이용약관 동의",
                        isChecked = termsOfUseCheck,
                        onCheckedChange = {
                            termsOfUseCheck = termsOfUseCheck.not()
                        },
                        link = ""
                    )
                    AgreeItem(
                        text = "(필수) 개인정보 처리방침",
                        isChecked = privacyCollectionCheck,
                        onCheckedChange = {
                            privacyCollectionCheck = privacyCollectionCheck.not()
                        }, link = ""
                    )
                    AgreeItem(
                        text = "(필수) 개인정보 제 3자 제공 동의",
                        isChecked = privacyUseCheck,
                        onCheckedChange = {
                            privacyUseCheck = privacyUseCheck.not()
                        },
                        link = ""
                    )
                    AgreeItem(
                        text = "(선택) 주간 광고성 알림 수신 동의",
                        isChecked = dayTimeAdsCheck,
                        onCheckedChange = {
                            dayTimeAdsCheck = dayTimeAdsCheck.not()
                        })
                    AgreeItem(
                        text = "(선택) 야간 광고성 알림 수신 동의",
                        isChecked = nightAdsCheck,
                        onCheckedChange = {
                            nightAdsCheck = nightAdsCheck.not()
                        })
                }
            }


            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.White
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .height(96.dp)
                    .align(Alignment.BottomCenter)
            ) {
                BtnFilledLPrimary(
                    text = "다음",
                    onClick = {
                        viewModel.updateUser()
                        if (nightAdsCheck) {
                            viewModel.patchPushSettingNight()
                            FirebaseMessaging.getInstance()
                                .subscribeToTopic(AlarmType.night_alarm.name)
                        }
                        if (dayTimeAdsCheck) {
                            viewModel.patchPushSettingDay()
                            FirebaseMessaging.getInstance()
                                .subscribeToTopic(AlarmType.day_alarm.name)
                        }
                    },
                    enabled = childrenCheck && termsOfUseCheck && privacyCollectionCheck && privacyUseCheck,
                    modifier = Modifier
                        .align(BottomCenter)
                        .padding(bottom = 32.dp)
                )
            }
        }
    }
}

@Composable
fun AgreeItem(text: String, isChecked: Boolean, onCheckedChange: () -> Unit, link: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = text,
                style = FanwooriTypography.body3,
                color = Gray800,
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 16.dp)
            )

            link?.let {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 2.dp)
                        .align(CenterVertically),
                    tint = Gray750
                )
            }
        }

        CheckBoxNullText(
            isChecked = isChecked, onCheckedChange = {
                onCheckedChange()
            }, modifier = Modifier
                .align(CenterVertically)
                .padding(end = 16.dp)
        )
    }
}


@Preview
@Composable
fun TermsAgreementPreview() {
    FanwooriTheme {
        TermsAgreement(
            onConfirmClick = {},
        )
    }
}