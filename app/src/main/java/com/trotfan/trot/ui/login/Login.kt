package com.trotfan.trot.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.UserTokenValue
import com.trotfan.trot.model.userTokenStore
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Primary600
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onKakaoSignInOnClick: () -> Unit,
    onAppleSignInOnClick: () -> Unit,
    onGoogleSignInOnClick: () -> Unit
) {
    val context = LocalContext.current
    val accessToken =
        context.userTokenStore.data.collectAsState(initial = UserTokenValue.getDefaultInstance()).value.accessToken
    var isSignIn by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginButton(
                text = "카카오톡 계정으로 로그인",
                icon = null,
                textColor = Gray800,
                backgroundColor = Color(0XFFFEE500)
            ) {
                onKakaoSignInOnClick()
            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Apple 계정으로 로그인",
                icon = painterResource(id = R.drawable.apple_symbol)
            ) {
                onAppleSignInOnClick()
            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Google 계정으로 로그인",
                icon = painterResource(id = R.drawable.google_symbol)
            ) {
                onGoogleSignInOnClick()
            }

            Text(
                text = "가입을 진행할 경우,\n" +
                        "아래의 정책에 동의한 것으로 간주됩니다.",
                textAlign = TextAlign.Center,
                style = FanwooriTypography.caption1,
                color = Gray600,
                modifier = Modifier.padding(top = 32.dp)
            )

            Row(
                modifier = Modifier.padding(bottom = 32.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable() {},
                    text = "이용약관",
                    color = Primary600,
                    style = FanwooriTypography.button2,
                )
                Spacer(
                    modifier = Modifier
                        .background(Primary600)
                        .width(1.dp)
                        .height(8.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { },
                    text = "개인정보처리방침",
                    color = Primary600,
                    style = FanwooriTypography.button2
                )
            }
        }
        if (accessToken != null) {
            isSignIn = true
        }
    }
}