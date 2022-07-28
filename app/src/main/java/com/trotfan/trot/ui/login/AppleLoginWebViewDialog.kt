package com.trotfan.trot.ui.login

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray400
import com.trotfan.trot.ui.theme.Primary300

@Composable
fun AppleLoginWebViewDialog(url: String, onDismissRequest: () -> Unit) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(MaterialTheme.colorScheme.surface)
        ) {


            var value by remember { mutableStateOf("") }
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

//            AndroidView(factory = {
//                WebView(context).apply {
//                    webViewClient = WebViewClient()
//                    loadUrl(url)
//                }
//            })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                value = value,
                singleLine = true,
                onValueChange = { value = it },
                label = { Text(text = "닉네임") },
                shape = RoundedCornerShape(12.dp),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Primary300,
                    unfocusedBorderColor = Color.Transparent,
                    placeholderColor = Gray400,
                    focusedLabelColor = Primary300,
                    unfocusedLabelColor = Gray400,
                    containerColor = Gray100
                )
            )
        }
    }
}