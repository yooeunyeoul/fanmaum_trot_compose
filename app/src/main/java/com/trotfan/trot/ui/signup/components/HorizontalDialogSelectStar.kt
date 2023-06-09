package com.trotfan.trot.ui.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.components.chip.ChipFilledLSecondary
import com.trotfan.trot.ui.theme.*

@Composable
fun HorizontalDialogSelectStar(
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit,
    contentText: String,
    maxLength: Int = 8,
    positiveText: String,
    negativeText: String,
    onPositive: () -> Unit = { },
    onPositiveWithInputText: (String) -> Unit = { },
    onDismiss: () -> Unit
) {

    var inputText = ""
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(),
    ) {
        Surface(
            modifier = modifier
                .width(296.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 32.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content.invoke()

                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    ChipFilledLSecondary(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Secondary50),
                        text = contentText ?: "",
                        textColor = Secondary600
                    )

                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "님",
                        style = FanwooriTypography.body4,
                        color = Gray750
                    )
                }

                Text(
                    text = "을 선택하시겠어요?",
                    textAlign = TextAlign.Center,
                    style = FanwooriTypography.body4,
                    color = Gray750,
                    modifier = Modifier.padding(top = 16.dp)
                )


                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    BtnOutlineLGray(
                        text = negativeText,
                        modifier = Modifier.weight(1f)
                    ) {
                        onDismiss()
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    BtnFilledLPrimary(
                        text = positiveText,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (inputText.isEmpty()) {
                            onPositive()
                        } else {
                            onPositiveWithInputText.invoke(inputText)
                        }

                        onDismiss()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HorizontalTitleDialogPreview() {
    HorizontalDialogSelectStar(
        content = {
            Text(
                text = "내 스타 선택은\n" +
                        "최초 1회만 가능해요!",
                textAlign = TextAlign.Center,
                style = FanwooriTypography.subtitle1,
                color = Gray700
            )
        },
        contentText = "양파쿵야",
        positiveText = "확인",
        negativeText = "취소",
        modifier = Modifier,
        onDismiss = {

        }
    )
}
