package com.trotfan.trot.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.R
import com.trotfan.trot.datastore.PermissionAgreeStore
import com.trotfan.trot.datastore.PermissionAgreementManager
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.login.viewmodel.AuthViewModel
import com.trotfan.trot.ui.theme.Gray800
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleSignInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.googleLogin(task)
            } else {
                Log.d("googleAuth", result.toString())
            }
        }
    val userInfo by viewModel.userInfo.collectAsState()
    val serverAvailable by viewModel.serverAvailable.collectAsState()

    var isAppleLoginDialogOpen by rememberSaveable { mutableStateOf(false) }
    val secessionUserState by viewModel.secessionUserState.collectAsState()
    var userTokenState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.getServerState()
    }
    if (secessionUserState) {
        VerticalDialog(
            contentText = "탈퇴일로부터 90일 동안\n" +
                    "동일한 계정, 닉네임, 인증된 번호로\n" +
                    "재가입할 수 없어요.", buttonOneText = "확인"
        ) {
            coroutineScope.launch {
                viewModel.secessionUserState.emit(false)
            }
        }
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {

        Column(
            Modifier
                .fillMaxSize()
        ) {

            Column(
                Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.textlogo),
                    contentDescription = "로고"
                )

            }

            coroutineScope.launch {
                userTokenState = context.userTokenStore.data.first().token.isNullOrEmpty()
            }

            if (userTokenState) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginButton(
                        text = "카카오톡 계정으로 계속하기",
                        icon = painterResource(id = R.drawable.kakao_symbol),
                        textColor = Gray800,
                        backgroundColor = Color(0XFFFEE500),
                        borderWidth = 0.dp,
                    ) {
                        handleKakaoLogin(context, viewModel)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LoginButton(
                        text = "Google 계정으로 계속하기",
                        icon = painterResource(id = R.drawable.google_symbol)
                    ) {
                        googleSignIn(googleSignInLauncher, context)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LoginButton(
                        text = "Apple 계정으로 계속하기",
                        icon = painterResource(id = R.drawable.apple_symbol)
                    ) {
                        isAppleLoginDialogOpen = !isAppleLoginDialogOpen
                    }

                }
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
                val permissionAgreementManager =
                    PermissionAgreementManager(context.PermissionAgreeStore)

                if (permissionAgreementManager.isPermissionCheckFlow.first().not()) {
                    val step: String = if (userInfo!!.agrees_terms?.not() == true) {
                        "terms"
                    } else if (userInfo!!.star == null) {
                        "star"
                    } else if (userInfo!!.name == null) {
                        "nickname"
                    } else if (userInfo!!.phone_number == null) {
                        "phone"
                    } else if (userInfo!!.redeemed_code?.not() == true) {
                        "invite"
                    } else {
                        "complete"
                    }

                    routeSections(
                        navController,
                        "${Route.PermissionAgreement.route}/$step"
                    )
                } else if (userInfo!!.agrees_terms?.not() == true) {
                    routeSections(navController, Route.TermsAgreement.route)
                } else if (userInfo!!.star == null) {
                    routeSections(navController, Route.SelectStar.route)
                } else if (userInfo!!.name == null) {
                    routeSections(navController, Route.SettingNickname.route)
                } else if (userInfo!!.phone_number == null) {
                    routeSections(navController, Route.CertificationPhoneNumber.route)
                } else if (userInfo!!.redeemed_code?.not() == true) {
                    routeSections(navController, Route.InvitationCode.route)
                } else {
                    routeSections(navController, HomeSections.Vote.route)
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
        popUpTo(Route.Login.route) {
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
                Toast.makeText(context, "로그인 실패 + $error", Toast.LENGTH_SHORT).show()
                Log.d("AuthViewModel", "로그인 실패 + $error")
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Toast.makeText(context, "로그인 실패 + $error", Toast.LENGTH_SHORT).show()
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