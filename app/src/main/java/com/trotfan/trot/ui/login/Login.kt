package com.trotfan.trot.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.R
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.AppleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.login.viewmodel.AuthViewModel
import com.trotfan.trot.ui.signup.SignUpSections
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val googleSignInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.googleLogin(task)
            } else {
                Log.d("googleAuth", result.toString())
            }
        }

    val userId = flow {
        context.userIdStore.data.map {
            it.userId
        }.collect(collector = {
            this.emit(it)
        })
    }.collectAsState(initial = 0)
    val userToken by viewModel.userToken.collectAsState()
    val userInfo by viewModel.userInfo.collectAsState()
    val serverAvailable by viewModel.serverAvailable.collectAsState()

    var isAppleLoginDialogOpen by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginButton(
                text = "카카오톡 계정으로 로그인",
                icon = painterResource(id = R.drawable.kakao_symbol),
                textColor = Gray800,
                backgroundColor = Color(0XFFFEE500),
                borderWidth = 0.dp,
            ) {
                handleKakaoLogin(context, viewModel)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Apple 계정으로 로그인",
                icon = painterResource(id = R.drawable.apple_symbol)
            ) {
                isAppleLoginDialogOpen = !isAppleLoginDialogOpen
            }
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                text = "Google 계정으로 로그인",
                icon = painterResource(id = R.drawable.google_symbol)
            ) {
                googleSignIn(googleSignInLauncher, context)
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
                    color = Secondary800,
                    style = FanwooriTypography.button2,
                )
                Spacer(
                    modifier = Modifier
                        .background(Secondary800)
                        .width(1.dp)
                        .height(8.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { },
                    text = "개인정보처리방침",
                    color = Secondary800,
                    style = FanwooriTypography.button2
                )
            }
        }

        if (isAppleLoginDialogOpen) {
            AppleLoginWebViewDialog {
                isAppleLoginDialogOpen = false
                Log.d("AuthViewModel", it.toString())
                it?.let {
                    viewModel.postAppleToken(it)
                }
            }
        }

        if (userInfo != null) {
            LaunchedEffect(userInfo) {
                if (userInfo!!.star == null) {
                    routeSections(navController, SignUpSections.SelectStar.route)
                } else if (userInfo!!.name == null) {
                    routeSections(navController, SignUpSections.SettingNickName.route)
                } else if (userInfo!!.phone_number == null) {
                    routeSections(navController, SignUpSections.CertificationPhoneNumber.route)
                } else if (userInfo!!.redeem_code == null) {
                    routeSections(navController, SignUpSections.InvitationCode.route)
                } else {
                    routeSections(navController, HomeSections.VOTE.route)
                }
            }
        }

        if (serverAvailable.not()) {
            AlertDialog(
                onDismissRequest = {
                },
                text = {
                    Text("보다 안정적인 서비스를 제공하기 위해 서버를 점검하고 있습니다. 빠르게 점검하고 돌아올게요!")
                },
                confirmButton = {
                    TextButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Text("확인")
                    }
                }
            )
        }
    }
}


fun routeSections(nevController: NavController, route: String) {
    nevController.navigate(route) {
        popUpTo(LoginNav.Login.route) {
            inclusive = true
        }
    }
}


fun googleSignIn(
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.google_server_client_id))
        .requestId()
        .requestServerAuthCode(context.getString(R.string.google_server_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val googleIntent = googleSignInClient.signInIntent
    launcher.launch(googleIntent)
}


private fun handleKakaoLogin(context: Context, viewModel: AuthViewModel) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (token != null) {
                kakaoLoginSuccess(token = token, viewModel)
            } else if (error != null) {
                Log.d("AuthViewModel", "로그인 실패 + $error")
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Log.d("AuthViewModel", "로그인 실패 + $error")
            } else if (token != null) {
                kakaoLoginSuccess(token = token, viewModel)
            }
        }
    }
}

private fun kakaoLoginSuccess(token: OAuthToken, viewModel: AuthViewModel) {
    val kakaoTokens = KakaoTokens(
        refresh_token = token.refreshToken,
        access_token = token.accessToken,
        id_token = token.idToken
    )
    viewModel.postKakaoToken(kakaoTokens)
    Log.d(
        "AuthViewModel",
        "로그인 성공 refresh = ${token.refreshToken}\naccess = ${token.accessToken}\nid = ${token.idToken}"
    )
}