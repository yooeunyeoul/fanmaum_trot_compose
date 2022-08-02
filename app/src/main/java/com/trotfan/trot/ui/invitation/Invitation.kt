package com.trotfan.trot.ui.invitation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.components.InputTextField
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
                }
            },
            errorMessage = errorMessage
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            dynamicClick()
        }) {
            Text(text = "다이나믹 링크 생성")
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