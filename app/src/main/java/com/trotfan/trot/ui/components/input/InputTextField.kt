package com.trotfan.trot.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.dpToSp

@Composable
fun InputTextField(
    text: String = "",
    placeHolder: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    errorStatus: Boolean = false,
    positiveStatus: Boolean = false,
    errorMessage: String? = null,
    successMessage: String? = null,
    modifier: Modifier,
    keyboardOptions: KeyboardOptions? = null,
    trailingIconDisabled: Boolean = false
) {
    var value by remember { mutableStateOf(text) }
    val focusBorderColor =
        if (positiveStatus) SemanticPositive300 else Gray600

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            value = text,
            singleLine = true,
            isError = errorStatus,
            onValueChange = {
                if (it.length <= maxLength) value = it
                onValueChange(value)
            },
            placeholder = { Text(text = placeHolder, fontSize = dpToSp(dp = 17.dp)) },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            trailingIcon = {
                if (!trailingIconDisabled) {
                    if (value.isBlank()) return@OutlinedTextField else Icon(
                        painterResource(id = R.drawable.input_clear),
                        null,
                        Modifier.clickable {
                            value = ""
                            onValueChange(value)
                        },
                        tint = Gray700
                    )
                }

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Gray100,
                focusedBorderColor = focusBorderColor,
                unfocusedBorderColor = Color.Transparent,
                placeholderColor = Gray600,
                focusedLabelColor = Primary300,
                leadingIconColor = Color.Black,
                unfocusedLabelColor = Gray600,
                errorBorderColor = SemanticNegative300,
                errorTrailingIconColor = Gray600,
                textColor = Gray900
            ),
            keyboardOptions = keyboardOptions ?: KeyboardOptions(),
            textStyle = TextStyle(
                fontSize = dpToSp(dp = 17.dp),
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium
            )
        )

        if (errorStatus || positiveStatus) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, start = 6.dp)
                    .width(236.dp),
                text = if (errorStatus) errorMessage ?: "" else successMessage ?: "",
                color = if (errorStatus) SemanticNegative500 else SemanticPositive500,
                style = FanwooriTypography.caption1,
            )
        }
    }
}