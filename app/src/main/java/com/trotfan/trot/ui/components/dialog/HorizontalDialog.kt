package com.trotfan.trot.ui.components.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.components.input.InputTextField
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray750
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary700
import com.trotfan.trot.ui.utils.drawColoredShadow

@RequiresApi(Build.VERSION_CODES.O)
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

    var inputText by remember { mutableStateOf("") }
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
                    color = Gray800
                )

                contentText?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        style = FanwooriTypography.body4,
                        color = Gray750,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                inputPlaceHolderText?.let {
                    InputTextField(
                        text = inputText,
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
                    BtnOutlineLGray(
                        text = negativeText,
                        modifier = Modifier.weight(1f)
                    ) {
                        onDismiss()
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    BtnFilledLPrimary(
                        text = positiveText,
                        enabled = positiveButtonEnabled,
                        modifier = Modifier.weight(1f)
                            .drawColoredShadow(color = Primary700)
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

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
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