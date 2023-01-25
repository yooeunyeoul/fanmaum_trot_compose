package com.trotfan.trot.ui.permission

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.checkbox.CheckBoxNullText
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgreementBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    onConfirmClick: (String) -> Unit
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


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_close),
            contentDescription = null,
            modifier = Modifier.size(24.dp).clickable {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
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
            }, link = "")
        AgreeItem(text = "(필수) 개인정보 제 3자 제공 동의", isChecked = privacyUseCheck, onCheckedChange = {
            privacyUseCheck = privacyUseCheck.not()
        }, link = "")
        AgreeItem(text = "(선택) 주간 광고성 알림 수신 동의", isChecked = dayTimeAdsCheck, onCheckedChange = {
            dayTimeAdsCheck = dayTimeAdsCheck.not()
        })
        AgreeItem(text = "(선택) 야간 광고성 알림 수신 동의", isChecked = nightAdsCheck, onCheckedChange = {
            nightAdsCheck = nightAdsCheck.not()
        })
        Spacer(modifier = Modifier.height(46.dp))
        BtnFilledLPrimary(
            text = "다음",
            onClick = {},
            enabled = termsOfUseCheck && privacyCollectionCheck && privacyUseCheck
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun AgreeItem(text: String, isChecked: Boolean, onCheckedChange: () -> Unit, link: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(modifier = Modifier.weight(1f).fillMaxHeight()) {
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


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MonthlyCalenderPickerPreview() {
    FanwooriTheme {
        AgreementBottomSheet(
            modalBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            ),
            onConfirmClick = {}
        )
    }
}