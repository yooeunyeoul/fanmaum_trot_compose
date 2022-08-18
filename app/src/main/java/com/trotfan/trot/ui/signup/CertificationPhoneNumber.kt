package com.trotfan.trot.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trotfan.trot.ui.components.button.ContainedButton
import com.trotfan.trot.ui.components.button.Outline1Button
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.signup.viewmodel.CertificationNumberCheckStatus
import com.trotfan.trot.ui.signup.viewmodel.CertificationPhoneNumberViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@Composable
fun CertificationPhoneScreen(
    navController: NavController,
    viewModel: CertificationPhoneNumberViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var inputPhoneNumber by remember {
        mutableStateOf("")
    }
    var errorState by remember {
        mutableStateOf(false)
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

    val certificationNumber by viewModel.certificationNumber.collectAsState()

    var ddd by remember {
        mutableStateOf("")
    }

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(
        hostState = snackState,
        Modifier
            .background(color = Color.White)
            .padding(top = 325.dp)
    )

    fun launchSnackBar() {
        snackScope.launch { snackState.showSnackbar(ddd) }
    }

    var inputCertificationNumber by remember {
        mutableStateOf("")
    }

    if (viewModel.onComplete.collectAsState().value) {
        LaunchedEffect(Unit) {
            navController.navigate(SignUpSections.InvitationCode.route) {
                popUpTo(SignUpSections.CertificationPhoneNumber.route) {
                    inclusive = true
                }
            }
        }
    }







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
                viewModel.updateUser(inputPhoneNumber)
            }
        }
        null -> {

        }
        CertificationNumberCheckStatus.Duplicate -> {
            VerticalDialog(
                contentText = status?.content ?: "",
                buttonOneText = status?.buttonText ?: ""
            ) {
                viewModel.hideCertificateDialog()
            }
        }
    }

    LaunchedEffect(certificationNumberSend && ticks != 0) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
    ) {
        CustomTopAppBar(title = "회원가입")
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "휴대폰 번호를\n" +
                    "인증해주세요",
            color = Gray700,
            style = FanwooriTypography.h1
        )

        Text(
            text = "공정한 투표를 위해 번호인증이 필요해요.",
            color = Gray500,
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
                    text = inputPhoneNumber,
                    placeHolder = "예) 01012345678",
                    maxLength = 11,
                    errorStatus = errorState,
                    onValueChange = {
                        inputPhoneNumber = it
                        errorState =
                            inputPhoneNumber.length > 3 && inputPhoneNumber.substring(
                                0,
                                3
                            ) == "010" == false
                    },
                    errorMessage = "휴대폰 번호가 형식에 맞지\n" +
                            "않아요.",
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Outline1Button(
                text = "인증번호 받기",
                enabled = inputPhoneNumber.length >= 11 && !errorState,
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .width(120.dp)
            ) {
                ticks = 180
                certificationNumberSend = true
                focusManager.clearFocus()
                focusRequester.requestFocus()
                val randomCode = (111111..999999).shuffled().last().toString()
                ddd = randomCode
                launchSnackBar()
                viewModel.requestCertificationCode(inputPhoneNumber, randomCode)


            }
        }
        if (errorState) {
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 6.dp),
                text = "휴대폰 번호가 형식에 맞지\n" +
                        "않아요.",
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
                        inputCertificationNumber = it
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
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                ContainedButton(
                    text = "인증하기",
                    enabled = inputCertificationNumber.length == 6,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.checkAuthNumber(
                        number = inputCertificationNumber,
                        time = ticks
                    )
                }
            }

        }
    }
}
