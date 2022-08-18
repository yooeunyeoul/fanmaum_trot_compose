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
import com.trotfan.trot.ui.components.button.ContainedButton
import com.trotfan.trot.ui.components.button.Outline2Button
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700

@Composable
fun HorizontalDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    contentText: String? = null,
    inputPlaceHolderText: String? = null,
    maxLength: Int = 8,
    positiveText: String,
    negativeText: String,
    onPositive: () -> Unit = { },
    onPositiveWithInputText: (String) -> Unit = { },
    positiveButtonEnabled: Boolean = true,
    onInputText: (String) -> Unit = {},
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

                contentText?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        style = FanwooriTypography.body4,
                        color = Gray600,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                inputPlaceHolderText?.let {
                    InputTextField(
                        placeHolder = inputPlaceHolderText, onValueChange = {
                            inputText = it
                            onInputText.invoke(it)
                        }, maxLength = maxLength,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

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

                    ContainedButton(
                        text = positiveText,
                        enabled = positiveButtonEnabled,
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
    HorizontalDialog(
        titleText = "title",
        contentText = "text text\n" +
                "vertical 1-button",
        positiveText = "확인",
        negativeText = "취소",
        modifier = Modifier,
        onDismiss = {

        }
    )
}

@Preview
@Composable
fun HorizontalInputDialogPreview() {
    HorizontalDialog(
        titleText = "title",
        positiveText = "확인",
        negativeText = "취소",
        inputPlaceHolderText = "input",
        maxLength = 12,
        modifier = Modifier,
        onDismiss = {

        }
    )
}