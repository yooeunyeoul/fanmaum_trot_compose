package com.trotfan.trot.ui.invitation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.components.button.ContainedButton
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.components.button.Outline1Button
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Gray700
import java.util.regex.Pattern

@Composable
fun InvitationScreen(
    linkText: String = "",
    dynamicClick: () -> Unit
) {
    var errorState by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(linkText) }
    var completeState by remember { mutableStateOf(false) }
    var skipDialogState by remember { mutableStateOf(false) }
    var completeDialogState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "친구에게 받은\n초대코드를 입력해주세요",
            color = Gray700,
            style = FanwooriTypography.h1
        )

        Text(
            text = "나와 친구 모두 타임투표권 500표를 받을 수 있어요.",
            color = Gray500,
            modifier = Modifier.padding(top = 8.dp),
            style = FanwooriTypography.caption1
        )

        Spacer(modifier = Modifier.height(32.dp))

        InputTextField(
            placeHolder = "#8자리 코드",
            maxLength = 8,
            errorStatus = errorState,
            onValueChange = {
                if (it.isNotBlank() && it[0] != '#') {
                    errorState = true
                    if (errorState) {
                        errorMessage = "초대코드는 #으로 시작해요"
                    }
                } else if (!Pattern.matches("^[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣#]*\$", it)) {
                    errorState = true
                    if (errorState) {
                        errorMessage = "#을 제외한 특수문자와 공백은 입력할 수 없어요."
                    }
                } else {
                    errorState = false
                    completeState = errorState.not() && it.length == 8
                }
            },
            errorMessage = errorMessage,
            modifier = Modifier
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {

            Outline1Button(text = "건너뛰기", modifier = Modifier.weight(1f)) {
                skipDialogState = true
            }

            Spacer(modifier = Modifier.width(8.dp))

            ContainedButton(text = "완료", enabled = completeState, modifier = Modifier.weight(1f)) {
                completeDialogState = true
            }
        }

        if (skipDialogState) {
            HorizontalDialog(
                titleText = "초대코드 입력을 건너 뛸까요?",
                contentText = "지금 건너뛰면,\n" +
                        "친구 초대 보상을 받을 수 없어요.",
                positiveText = "건너뛰기",
                negativeText = "취소",
                onDismiss = { skipDialogState = false },
                onPositive = {}
            )
        }
        
        if (completeDialogState) {
            VerticalDialog(
                contentText = "타임투표권 500표 받았어요!",
                buttonOneText = "확인",
                onDismiss = { completeDialogState = false }
            )
        }
    }
}

@Preview
@Composable
fun InvitationPreview() {
    FanwooriTheme {
        InvitationScreen {}
    }
}