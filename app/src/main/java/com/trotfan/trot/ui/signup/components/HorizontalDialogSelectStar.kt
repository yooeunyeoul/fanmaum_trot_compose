package com.trotfan.trot.ui.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.ui.components.button.ContainedLargeButton
import com.trotfan.trot.ui.components.button.Outline2Button
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.theme.*

@Composable
fun HorizontalDialogSelectStar(
    modifier: Modifier = Modifier,
    titleText: String,
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
                Text(
                    text = titleText,
                    textAlign = TextAlign.Center,
                    style = FanwooriTypography.subtitle1,
                    color = Gray700
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    Box(
                        Modifier
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Secondary100
                            )
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                                top = 8.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contentText,
                            style = FanwooriTypography.subtitle1,
                            color = Secondary600,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "님",
                        style = FanwooriTypography.body4,
                        color = Gray600
                    )
                }

                Text(
                    text = "을 선택하시겠어요?",
                    textAlign = TextAlign.Center,
                    style = FanwooriTypography.body4,
                    color = Gray600,
                    modifier = Modifier.padding(top = 16.dp)
                )


                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    Outline2Button(
                        text = negativeText,
                        modifier = Modifier.weight(1f)
                    ) {
                        onDismiss()
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    ContainedLargeButton(
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
        titleText = "title",
        contentText = "양파쿵야",
        positiveText = "확인",
        negativeText = "취소",
        modifier = Modifier,
        onDismiss = {

        }
    )
}
