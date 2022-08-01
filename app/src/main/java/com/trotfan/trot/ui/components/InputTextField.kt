package com.trotfan.trot.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
            .padding(16.dp)
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
        trailingIcon = { painterResource(id = R.drawable.input_clear) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary300,
            unfocusedBorderColor = Color.Transparent,
            placeholderColor = Gray400,
            focusedLabelColor = Primary300,
            unfocusedLabelColor = Gray400,
            errorBorderColor = SemanticNegative300
        )
    )
}