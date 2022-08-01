package com.trotfan.trot.ui.invitation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.components.CustomTopAppBar
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InvitationScreen() {
    Scaffold(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
        topBar = {
            CustomTopAppBar("회원가입")
        },
        content = {
            Text(
                modifier = Modifier
                    .fillMaxHeight(),
                text = "친구에게 받은\n초대코드를 입력해주세요",
                color = Gray700,
                style = FanwooriTypography.h1
            )
        }
    )
}

@Preview
@Composable
fun InvitationPreview() {
    FanwooriTheme {
        InvitationScreen()
    }
}