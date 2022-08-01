package com.trotfan.trot.ui.invitation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.components.CustomTopAppBar
import com.trotfan.trot.ui.components.InputTextField
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Gray700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InvitationScreen() {
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
        
        InputTextField(placeHolder = "#8자리 코드")
    }
}

@Preview
@Composable
fun InvitationPreview() {
    FanwooriTheme {
        InvitationScreen()
    }
}