package com.trotfan.trot.ui.home.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.trotfan.trot.R
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLPrimary
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.clickableSingle
import kotlinx.coroutines.launch

@Composable
fun SettingSecession(
    navController: NavController? = null
) {
    val reasons = listOf(
        "더 이상 스타에게 관심이 없어요",
        "원하는 스타가 없어요",
        "서비스 운영이 마음에 들지 않아요",
        "금전적인 부담이 돼요",
        "자주 사용하지 않아요",
        "다른 계정을 사용하고 싶어요",
        "기타 (직접입력)"
    )
    val (selected, setSelected) = remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    var isEtc by remember {
        mutableStateOf(false)
    }
    var etcText by remember {
        mutableStateOf("")
    }

    var secessionPage by remember {
        mutableStateOf(1)
    }

    var isChecked by remember {
        mutableStateOf(false)
    }


    val focusRequester = remember { FocusRequester() }

    var secessionDialogState by remember {
        mutableStateOf(false)
    }

    var secessionConfirmDialogState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppbarMLeftIcon(title = "회원탈퇴", icon = R.drawable.icon_back, onIconClick = {
                navController?.popBackStack()
            })

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                if (secessionPage == 1) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "팬마음을 떠나시려하는\n" +
                                "이유가 무엇인가요? \uD83E\uDD72",
                        fontSize = 24.sp,
                        color = Gray900,
                        fontFamily = pretendard,
                        fontStyle = FontStyle(R.font.pretendard_medium),
                        fontWeight = FontWeight(600),
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "팬마음이 더 나은 서비스를 제공할 수 있도록,\n" +
                                "소중한 의견 부탁드립니다.",
                        style = FanwooriTypography.caption1,
                        color = Gray700,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    SecessionRadioGroup(
                        items = reasons,
                        selected = selected,
                        setSelected = setSelected,
                        setEtcState = {
                            isEtc = it
                        })
                    if (isEtc) {
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                            scrollState.scrollTo(scrollState.maxValue)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                                .padding(start = 24.dp, end = 24.dp)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                                .background(Gray50)
                        ) {
                            TextField(
                                value = etcText,

                                onValueChange = { text ->
                                    etcText = text
                                },
                                placeholder = {
                                    Text(text = "20자 이상 적어주세요.")
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Gray900,
                                    placeholderColor = Gray600,
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = Gray900
                                ),
                                modifier = Modifier
                                    .background(Gray50)
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                            )
                        }
                        Spacer(modifier = Modifier.height(104.dp))
                    }
                } else {
                    LaunchedEffect(Unit) {
                        scrollState.scrollTo(0)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "팬마음 탈퇴 전, 꼭 확인해주세요!",
                        style = FanwooriTypography.subtitle2,
                        color = SemanticNegative500,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    SecessionCheckText(
                        number = "1.",
                        text = "탈퇴일로부터 90일 동안 동일한 계정, 닉네임, 인증된 번호로 재가입할 수 없어요."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecessionCheckText(
                        number = "2.",
                        text = "탈퇴 후 90일이 지나고 동일한 계정으로 재가입하더라도 탈퇴 전에 사용하던 모든 정보는 복구할 수 없어요."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecessionCheckText(
                        number = "3.",
                        text = "탈퇴 후 삭제되는 정보 : 90일이 지나면 개인정보, 보유 중인 재화내역은 완전히 삭제돼요."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecessionCheckText(
                        number = "4.",
                        text = "탈퇴 후 남아있는 정보 : 투표 참여 내역은 삭제되지 않아요."
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "[추가 유의사항]",
                        style = FanwooriTypography.body2,
                        color = Gray800,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SecessionCheckText(
                        number = "-",
                        text = "유료 결제를 통해 충전한 재화는 탈퇴 시 복구나 환불이 어렵습니다."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecessionCheckText(
                        number = "-",
                        text = "개인정보처리 방침 제 5조(개인정보 및 이용기간)에 따라 3개월 분리 보관 후 파기하며, 이외 용도로 이용하지 않습니다."
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(Gray50)
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .align(CenterVertically)
                                .size(22.dp)
                                .background(Color.White)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    isChecked = isChecked.not()

                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary500,
                                    uncheckedColor = Gray300
                                ),
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                        Text(
                            text = "안내 사항을 모두 확인하였으며,\n" +
                                    "이에 동의합니다", style = FanwooriTypography.body3, color = Gray800,
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(top = 16.dp, start = 12.dp, bottom = 16.dp)
                                .clickable {
                                    isChecked = isChecked.not()
                                }
                        )
                        if (isChecked) {
                            LaunchedEffect(Unit) {
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                            .fillMaxWidth(),
                        contentAlignment = BottomCenter
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            BtnOutlineLPrimary(
                                text = "취소",
                                modifier = Modifier
                                    .weight(1f)
                                    .align(CenterVertically)
                            ) {

                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            BtnFilledLPrimary(
                                text = "회원탈퇴",
                                modifier = Modifier
                                    .weight(1f)
                                    .align(CenterVertically),
                                enabled = isChecked
                            ) {

                            }
                        }
                    }
                }
            }
        }

        if (secessionPage == 1) {
            Box(
                contentAlignment = BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
            ) {
                BtnFilledLPrimary(text = "다음", enabled = selected.isEmpty().not()) {
                    secessionPage = 2
                }
            }
        }

        if (secessionDialogState) {
            HorizontalDialog(
                titleText = "정말로 회원탈퇴를 하시겠습니까?",
                positiveText = "확인",
                negativeText = "취소",
                onPositive = {
                    secessionDialogState = false
                    secessionConfirmDialogState = true
                },
                onDismiss = {
                    secessionDialogState = false
                }
            )
        }

        if (secessionConfirmDialogState) {
            VerticalDialog(
                contentText = "회원탈퇴 되었습니다",
                buttonOneText = "확인",
                onDismiss = {
                    coroutineScope.launch {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail().build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        googleSignInClient.signOut().addOnCompleteListener {
                            navController?.navigate(Route.Login.route) {
                                secessionConfirmDialogState = false
                            }
                        }
                        context.userIdStore.updateData {
                            it.toBuilder().setUserId(0).build()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SecessionCheckText(number: String, text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = number,
            style = FanwooriTypography.caption1,
            color = Gray800,
            modifier = Modifier.width(16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = FanwooriTypography.caption1, color = Gray800)
    }
}

@Composable
fun SecessionRadioGroup(
    items: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
    setEtcState: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items.forEach { item ->
            Box {
                RadioButton(
                    selected = selected == item, onClick = {
                        setSelected(item)
                        setEtcState(item == "기타 (직접입력)")
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary500,
                        unselectedColor = Gray500
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = item,
                    style = FanwooriTypography.body3,
                    color = Gray800,
                    modifier = Modifier
                        .align(CenterStart)
                        .padding(start = 52.dp)
                        .clickableSingle {
                            setSelected(item)
                            setEtcState(item == "기타 (직접입력)")
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingSecessionPreview() {
    FanwooriTheme {
        SettingSecession()
    }
}