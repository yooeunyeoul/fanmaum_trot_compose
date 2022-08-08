package com.trotfan.trot.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*

@Composable
fun InputTextField(
    placeHolder: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    errorStatus: Boolean = false,
    positiveStatus: Boolean = false,
    errorMessage: String? = null,
    successMessage: String? = null,
    modifier: Modifier
) {
    var value by remember { mutableStateOf("") }
    val focusBorderColor =
        if (positiveStatus) SemanticPositive300 else Primary300

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = value,
            singleLine = true,
            isError = errorStatus,
            onValueChange = {
                if (it.length <= maxLength) value = it
                onValueChange(value)
            },
            placeholder = { Text(text = placeHolder) },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            trailingIcon = {
                if (value.isBlank()) return@OutlinedTextField else Icon(
                    painterResource(id = R.drawable.input_clear),
                    null,
                    Modifier.clickable {
                        value = ""
                        onValueChange(value)
                    }
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Gray100,
                focusedBorderColor = focusBorderColor,
                unfocusedBorderColor = Color.Transparent,
                placeholderColor = Gray400,
                focusedLabelColor = Primary300,
                leadingIconColor = Color.Black,
                unfocusedLabelColor = Gray400,
                errorBorderColor = SemanticNegative300,
                errorTrailingIconColor = Gray600
            )
        )

        if (errorMessage.isNullOrEmpty().not() && successMessage.isNullOrEmpty().not()) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, start = 6.dp)
                    .width(236.dp),
                text = if (errorStatus) errorMessage ?: "" else successMessage ?: "",
                color = if (errorStatus) SemanticNegative500 else SemanticPositive500,
                style = FanwooriTypography.caption1
            )
        }
    }
}