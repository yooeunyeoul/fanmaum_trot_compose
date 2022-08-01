package com.trotfan.trot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray400
import com.trotfan.trot.ui.theme.Primary300
import com.trotfan.trot.ui.theme.SemanticNegative300

@Composable
fun InputTextField(
    placeHolder: String
) {
    var value by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        singleLine = true,
        isError = false,
        onValueChange = { value = it },
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
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Gray100,
            focusedBorderColor = Primary300,
            unfocusedBorderColor = Color.Transparent,
            placeholderColor = Gray400,
            focusedLabelColor = Primary300,
            leadingIconColor = Color.Black,
            unfocusedLabelColor = Gray400,
            errorBorderColor = SemanticNegative300
        )
    )
}