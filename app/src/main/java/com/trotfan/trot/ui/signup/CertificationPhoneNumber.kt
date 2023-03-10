package com.trotfan.trot.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLPrimary
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.signup.viewmodel.CertificationNumberCheckStatus
import com.trotfan.trot.ui.signup.viewmodel.CertificationPhoneNumberViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


enum class RequestButtonText(val text: String) {
    ReceiveCode("인증번호 받기"), REQUEST("재전송")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CertificationPhoneScreen(
    navController: NavController,
    viewModel: CertificationPhoneNumberViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var inputPhoneNumber by remember {
        mutableStateOf("")
    }
    var requestedInputNumber by remember {
        mutableStateOf("")
    }
    var errorState by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }
    var certificationNumberSend by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var ticks by remember { mutableStateOf(180) }
    val second: String = (ticks.toLong() % 60).toString()
    val minute: String = (ticks.toLong() / 60 % 60).toString()

    val status by viewModel.certificationNumberStatus.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    val inputCertificationNumber by viewModel.inputCertificationNumber.collectAsState()


    when (status) {
        CertificationNumberCheckStatus.TimeExceeded,
        CertificationNumberCheckStatus.NotAuth -> {
            VerticalDialog(
                contentText = status?.content ?: "",
                buttonOneText = status?.buttonText ?: ""
            ) {
                viewModel.hideCertificateDialog()
            }
        }
        CertificationNumberCheckStatus.AuthSuccess -> {
            VerticalDialog(
                contentText = status?.content ?: "",
                buttonOneText = status?.buttonText ?: ""
            ) {
                navController.navigate(Route.InvitationCode.route) {
                    popUpTo(Route.CertificationPhoneNumber.route) {
                        inclusive = true
                    }
                }

            }
        }
        CertificationNumberCheckStatus.RequestSuccess -> {
            LaunchedEffect(key1 = Unit, block = {
                certificationNumberSend = true
            })
        }
        null -> {

        }
        CertificationNumberCheckStatus.Duplicate -> {
            errorMessage = CertificationNumberCheckStatus.Duplicate.content
            errorState = true
        }
        CertificationNumberCheckStatus.AutoApiRequest -> {
            LaunchedEffect(key1 = Unit, block = {
                viewModel.checkAuthNumber(
                    number = inputCertificationNumber,
                    phoneNumber = inputPhoneNumber,
                    time = ticks
                )
            })

        }
        else -> {

        }
    }

    LaunchedEffect(certificationNumberSend && ticks != 0) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
    }

    LaunchedEffect(Unit) {
        delay(200)
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.show()
                }
            }
    ) {
        CustomTopAppBar(title = "회원가입")
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "휴대폰 번호를\n" +
                    "인증해주세요",
            color = Gray900,
            style = FanwooriTypography.h1
        )

        Text(
            text = "공정한 투표를 위해 번호인증이 필요해요.",
            color = Gray700,
            modifier = Modifier.padding(top = 8.dp),
            style = FanwooriTypography.caption1
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.weight(1f)) {
                InputTextField(
                    placeHolder = "예) 01012345678",
                    text = inputPhoneNumber,
                    maxLength = 11,
                    errorStatus = errorState,
                    onValueChange = {
                        inputPhoneNumber = it
                        if (inputPhoneNumber.length > 3 && inputPhoneNumber.substring(
                                0,
                                3
                            ) != "010"
                        ) {
                            errorMessage = CertificationNumberCheckStatus.NotFitForm.content
                            errorState = true
                        } else {
                            errorState = false
                            viewModel.clearErrorState()
                        }
                    },
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            BtnOutlineLPrimary(
                text = if (inputPhoneNumber.length == 11 && certificationNumberSend && requestedInputNumber == inputPhoneNumber)
                    RequestButtonText.REQUEST.text else RequestButtonText.ReceiveCode.text,
                enabled = inputPhoneNumber.length >= 11 && !errorState,
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .width(120.dp)
            ) {

                ticks = 180
                requestedInputNumber = inputPhoneNumber
                focusManager.clearFocus()
                focusRequester.requestFocus()
                viewModel.requestCertificationCode(inputPhoneNumber)


            }
        }
        if (errorState) {
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 6.dp),
                text = errorMessage,
                style = FanwooriTypography.caption1,
                fontSize = 15.sp,
                color = SemanticNegative500
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (certificationNumberSend) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                InputTextField(
                    text = inputCertificationNumber,
                    placeHolder = "인증번호 6자리 입력",
                    maxLength = 6,
                    onValueChange = {
                        viewModel.changeCertificationNumber(it)
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    trailingIconDisabled = true
                )

                Text(
                    text = "$minute:${
                        if (second.length == 1)
                            "0$second"
                        else second
                    }",
                    style = FanwooriTypography.body2,
                    fontSize = 15.sp,
                    color = Secondary900,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                )

            }

        }

        DisposableEffect(certificationNumberSend) {
            if (certificationNumberSend) {
                focusRequester.requestFocus()
            }
            onDispose {}
        }

        if (certificationNumberSend) {

            BtnFilledLPrimary(
                text = "인증하기",
                enabled = inputCertificationNumber.length == 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                viewModel.checkAuthNumber(
                    number = inputCertificationNumber,
                    phoneNumber = inputPhoneNumber,
                    time = ticks
                )
            }
        }
    }
}
