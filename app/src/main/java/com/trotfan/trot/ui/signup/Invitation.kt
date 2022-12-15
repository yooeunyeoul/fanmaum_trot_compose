package com.trotfan.trot.ui.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLPrimary
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.signup.components.VerticalDialogInvitationComplete
import com.trotfan.trot.ui.signup.viewmodel.InvitationViewModel
import com.trotfan.trot.ui.signup.viewmodel.InviteCodeCheckStatus
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Gray700

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvitationScreen(
    modifier: Modifier = Modifier,
    linkText: String = "",
    navController: NavController,
    viewModel: InvitationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var skipDialogState by remember { mutableStateOf(false) }
    val completeDialogState by viewModel.completeStatus.collectAsState(initial = false)
    val skipState by viewModel.skipStatus.collectAsState(initial = false)

    val inviteCodeCheckState by viewModel.inviteCodeCheckStatus.collectAsState()
    val inviteCode by viewModel.inviteCode.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomTopAppBar(title = "회원가입")

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "친구에게 받은\n초대코드를 입력해주세요",
            color = Gray700,
            style = FanwooriTypography.h1
        )

        Text(
            text = "나와 친구 모두 500 투표권을 받을 수 있어요!",
            color = Gray500,
            modifier = Modifier.padding(top = 8.dp),
            style = FanwooriTypography.caption1
        )

        Spacer(modifier = Modifier.height(32.dp))

        InputTextField(
            text = inviteCode,
            placeHolder = "#8자리 코드",
            maxLength = 8,
            errorStatus = inviteCodeCheckState != InviteCodeCheckStatus.None,
            onValueChange = {
                viewModel.checkInviteCodeLocal(it)
            },
            errorMessage = inviteCodeCheckState.code,
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

            BtnOutlineLPrimary(text = "건너뛰기", modifier = Modifier.weight(1f)) {
                skipDialogState = true
            }

            Spacer(modifier = Modifier.width(8.dp))

            BtnFilledLPrimary(
                text = "완료",
                enabled = inviteCodeCheckState == InviteCodeCheckStatus.None && inviteCode.length == 8,
                modifier = Modifier.weight(1f)
            ) {
                viewModel.postInviteCode()
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
                onPositive = {
                    viewModel.postInviteCode()
                }
            )
        }

        if (completeDialogState) {
            VerticalDialogInvitationComplete(
                contentText = "타임투표권 500표 받았어요!",
                buttonOneText = "확인",
                onDismiss = {
                    navController.navigate(HomeSections.Vote.route) {
                        popUpTo(Route.InvitationCode.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        LaunchedEffect(skipState) {
            if (skipState) {
                navController.navigate(HomeSections.Vote.route) {
                    popUpTo(Route.InvitationCode.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun InvitationPreview() {
    FanwooriTheme {
        InvitationScreen(linkText = "#1234567", navController = rememberNavController())
    }
}