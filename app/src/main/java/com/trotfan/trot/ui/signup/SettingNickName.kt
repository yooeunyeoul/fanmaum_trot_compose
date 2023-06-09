package com.trotfan.trot.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLPrimary
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.signup.viewmodel.NickNameCheckStatus
import com.trotfan.trot.ui.signup.viewmodel.NickNameViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray900

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingNicknameScreen(
    navController: NavController,
    viewModel: NickNameViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val nickCheckState by viewModel.nickNameCheckStatus.collectAsState()
    val inputNickname by viewModel.inputNickName.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomTopAppBar(title = "회원가입")
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "팬마음에서 사용할\n" +
                    "닉네임을 정해볼까요?",
            color = Gray900,
            style = FanwooriTypography.h1
        )

        Text(
            text = "언제든지 변경할 수 있어요!",
            color = Gray700,
            modifier = Modifier.padding(top = 8.dp),
            style = FanwooriTypography.caption1
        )

        Spacer(modifier = Modifier.height(32.dp))
        Column {
            InputTextField(
                text = inputNickname,
                placeHolder = "닉네임 2~10자 입력",
                maxLength = 10,
                errorStatus = (nickCheckState != NickNameCheckStatus.None && nickCheckState != NickNameCheckStatus.AuthSuccess),
                positiveStatus = nickCheckState == NickNameCheckStatus.AuthSuccess,
                onValueChange = {
//                    inputText = it
                    viewModel.checkNickNameLocal(it)
                },
                errorMessage = nickCheckState.message,
                modifier = Modifier,
                successMessage = NickNameCheckStatus.AuthSuccess.message
            )

            Text(
                text = "${inputNickname.length} / 10",
                textAlign = TextAlign.End,
                style = FanwooriTypography.button2,
                color = Gray700,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        BtnOutlineLPrimary(
            text = "중복확인",
            enabled = inputNickname.length >= 2 && nickCheckState != NickNameCheckStatus.SpecialCharacterEmpty,
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.End)
                .padding(top = 14.dp)
        ) {
            viewModel.checkNickNameApi(inputNickname)
            keyboardController?.hide()
        }
    }



    Row(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        BtnFilledLPrimary(
            text = "다음",
            enabled = nickCheckState == NickNameCheckStatus.AuthSuccess,
            modifier = Modifier.weight(1f)
        ) {
            navController.navigate(Route.CertificationPhoneNumber.route) {
                popUpTo(Route.SettingNickname.route) {
                    inclusive = true
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSettingNickNameScreen() {
    SettingNicknameScreen(navController = rememberNavController())
}