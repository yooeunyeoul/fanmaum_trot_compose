package com.trotfan.trot.ui.home.vote.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.ContainedLargeButton
import com.trotfan.trot.ui.components.button.IconOutline3Button
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.addFocusCleaner
import com.trotfan.trot.ui.utils.clickable
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun VotingBottomSheet(votingBottomSheetState: ModalBottomSheetState, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val decimal = DecimalFormat("#,###")
    val myVoteCnt by remember { mutableStateOf(14000000) }
    var voteCnt by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()
    var voteBtnState by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isTextFieldFocused = false
    var placeholder by remember { mutableStateOf("얼마나 투표할까요?") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 600.dp)
            .addFocusCleaner(focusManager)

    ) {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_close),
                contentDescription = null,
                tint = Gray700,
                modifier = Modifier
                    .padding(start = 24.dp, top = 16.dp, bottom = 16.dp)
                    .clickable {
                        coroutineScope.launch {
                            votingBottomSheetState.hide()
                        }
                    }
            )
            Text(
                text = "투표",
                style = FanwooriTypography.body3,
                color = Gray700,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "내 투표권",
                style = FanwooriTypography.body2,
                color = Gray700,
                modifier = Modifier.padding(start = 32.dp, top = 32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = decimal.format(myVoteCnt),
                    style = FanwooriTypography.subtitle2,
                    color = Gray800,
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .weight(1f)
                )

                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 34.dp)
                ) {
                    Text(
                        text = "충전하기",
                        style = FanwooriTypography.button1,
                        color = Primary800,
                        modifier = Modifier.padding(start = 2.dp)
                    )

                    Box(
                        modifier = Modifier
                            .background(Primary800)
                            .width(62.dp)
                            .height(1.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 32.dp, end = 32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Primary50
                    )
            ) {
                Text(
                    text = "오늘 소멸 예정 투표권",
                    style = FanwooriTypography.body2,
                    color = Primary800,
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(start = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .align(CenterVertically),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vote_iconcolored),
                        contentDescription = null
                    )

                    Text(
                        text = "400",
                        style = FanwooriTypography.subtitle3,
                        color = Primary800,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }

            Text(
                text = "스타이름 님에게",
                style = FanwooriTypography.subtitle1,
                color = Gray800,
                modifier = Modifier
                    .padding(
                        top = 80.dp
                    )
                    .align(CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp)
                    .background(Gray50)
            ) {
                TextField(
                    value = voteCnt,
                    maxLines = 1,
                    onValueChange = { cnt ->
                        voteCnt = if (cnt.text.isBlank().not()) {
                            var decimalCnt = cnt.text.replace(",", "")
                            try {
                                if (decimalCnt.toLong() >= myVoteCnt) {
                                    decimalCnt = myVoteCnt.toString()
                                }
                                val formatText = decimal.format(decimalCnt.toLong())
                                TextFieldValue(
                                    formatText,
                                    selection = TextRange(formatText.length)
                                )
                            } catch (e: NumberFormatException) {
                                TextFieldValue(
                                    cnt.text.substring(0, cnt.text.length - 1),
                                    selection = TextRange(cnt.text.length - 1)
                                )
                            }

                        } else {
                            TextFieldValue("")
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = placeholder,
                            style = FanwooriTypography.h1,
                            textAlign = TextAlign.Center
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        placeholderColor = Gray400,
                        textColor = Gray900,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = FanwooriTypography.h1.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            isTextFieldFocused = it.isFocused
                            placeholder = if (isTextFieldFocused) {
                                ""
                            } else {
                                "얼마나 투표할까요?"
                            }
                        }
                )

                if (voteCnt.text.isBlank().not()) {
                    Image(
                        painter = painterResource(id = R.drawable.input_clear),
                        contentDescription = null,
                        modifier = Modifier
                            .align(CenterEnd)
                            .padding(end = 24.dp)
                            .clickable {
                                voteCnt = TextFieldValue("")
                            })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            IconOutline3Button(
                text = "모두사용",
                onClick = {
                    voteCnt = TextFieldValue(decimal.format(myVoteCnt))
                    focusManager.clearFocus()
                },
                icon = R.drawable.ic_vote_iconcolored,
                modifier = Modifier.align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(48.dp))

            ContainedLargeButton(
                text = "투표",
                enabled = voteBtnState,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            ) {
                coroutineScope.launch {
                    votingBottomSheetState.hide()
                    onDismiss()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        voteBtnState = voteCnt.text.isEmpty().not()

        if (isTextFieldFocused) {
            keyboardController?.hide()
        }
    }
}

@Preview
@Composable
fun VotingBottomSheetPreview() {
    FanwooriTheme {
    }
}