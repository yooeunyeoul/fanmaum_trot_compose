package com.trotfan.trot.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.R
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Composable
fun LoginScreen() {
    val context = LocalContext.current

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
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("Kakao Login", error.toString())
                    } else if (token != null) {
                        Log.e("Kakao Login", "로그인 성공 ${token.accessToken}")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Apple 계정으로 로그인",
                icon = painterResource(id = R.drawable.apple_symbol)
            ) {

            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Google 계정으로 로그인",
                icon = painterResource(id = R.drawable.google_symbol)
            ) {

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
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    FanwooriTheme {
        LoginScreen()
    }
}