package com.trotfan.trot.ui.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.ui.components.button.Outline1Button
import com.trotfan.trot.ui.components.button.TextButton
import com.trotfan.trot.ui.components.button.ContainedLargeButton
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600

@Composable
fun VerticalDialog(
    modifier: Modifier = Modifier,
    contentText: String,
    buttonOneText: String,
    buttonTwoText: String? = null,
    buttonThreeText: String? = null,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(),
    ) {
        Surface(
            modifier = modifier
                .width(328.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 12.dp,
                    top = 32.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = contentText,
                    textAlign = TextAlign.Center,
                    style = FanwooriTypography.body4,
                    color = Gray600
                )

                ContainedLargeButton(
                    text = buttonOneText,
                    modifier = Modifier.padding(
                        top = if (buttonTwoText.isNullOrEmpty()) 24.dp else 16.dp,
                        bottom = if (buttonTwoText.isNullOrEmpty()) 20.dp else 0.dp
                    )
                ) {
                    onDismiss()
                }

                buttonThreeText?.let {
                    Outline1Button(text = it, modifier = Modifier.padding(top = 8.dp)) {

                    }
                }

                buttonTwoText?.let {
                    TextButton(
                        text = it,
                        modifier = Modifier.padding(
                            top = if (buttonThreeText.isNullOrEmpty()) 16.dp else 8.dp
                        )
                    ) {
                        onDismiss()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun VerticalOneButtonDialogPreview() {
    VerticalDialog(
        contentText = "text text\n" +
                "vertical 1-button",
        buttonOneText = "확인",
        modifier = Modifier,
        onDismiss = {

        }
    )
}

@Preview
@Composable
fun VerticalTwoButtonDialogPreview() {
    VerticalDialog(
        contentText = "text text\n" +
                "vertical 2-button",
        buttonOneText = "확인",
        buttonTwoText = "취소",
        modifier = Modifier,
        onDismiss = {

        }
    )
}

@Preview
@Composable
fun VerticalThreeButtonDialogPreview() {
    VerticalDialog(
        contentText = "text text\n" +
                "vertical 3-button",
        buttonOneText = "확인",
        buttonTwoText = "취소",
        buttonThreeText = "확인2",
        modifier = Modifier,
        onDismiss = {

        }
    )
}