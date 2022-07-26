package com.trotfan.trot.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.theme.TrotTheme

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginButton("Apple 계정으로 로그인", painterResource(id = R.drawable.apple_symbol))
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton("Google 계정으로 로그인", painterResource(id = R.drawable.google_symbol))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TrotTheme {
        LoginScreen()
    }
}