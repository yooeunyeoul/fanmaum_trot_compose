package com.trotfan.trot.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.checkbox.CheckBoxNullText
import com.trotfan.trot.ui.login.viewmodel.AuthViewModel
import com.trotfan.trot.ui.signup.viewmodel.TermsViewModel
import com.trotfan.trot.ui.theme.*

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

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                    termsOfUseCheck && privacyCollectionCheck && privacyUseCheck && dayTimeAdsCheck && nightAdsCheck

                Spacer(modifier = Modifier.height(16.dp))
                AgreeItem(text = "(필수) 이용약관 동의", isChecked = termsOfUseCheck, onCheckedChange = {
                    termsOfUseCheck = termsOfUseCheck.not()
                }, link = "")
                AgreeItem(
                    text = "(필수) 개인정보 수집 및 이용 동의",
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


            BtnFilledLPrimary(
                text = "다음",
                onClick = {
                    viewModel.updateUser()
                    onConfirmClick()
                },
                enabled = termsOfUseCheck && privacyCollectionCheck && privacyUseCheck,
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(bottom = 32.dp)
            )
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
fun MonthlyCalenderPickerPreview() {
    FanwooriTheme {
        TermsAgreement(
            onConfirmClick = {}
        )
    }
}