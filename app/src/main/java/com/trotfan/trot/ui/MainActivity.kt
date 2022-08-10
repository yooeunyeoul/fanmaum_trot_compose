package com.trotfan.trot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.ui.login.AppleLoginWebViewDialog
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.ui.signup.CertificationPhoneScreen
import com.trotfan.trot.ui.signup.SearchStarScreen
import com.trotfan.trot.ui.signup.SelectStarScreen
import com.trotfan.trot.ui.signup.SettingNicknameScreen
import com.trotfan.trot.viewmodel.AuthViewModel
import com.trotfan.trot.viewmodel.InvitationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val invitationViewModel: InvitationViewModel by viewModels()

    private val googleSignInLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            authViewModel.googleLogin(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDynamicLink()
        setContent {
            TrotApp()
//            var isAppleLoginDialogOpen by rememberSaveable { mutableStateOf(false) }
//            Surface {
//                LoginScreen(
//                    onKakaoSignInOnClick = {
//                        handleKakaoLogin()
//                    },
//                    onAppleSignInOnClick = {
//                        isAppleLoginDialogOpen = !isAppleLoginDialogOpen
//                    },
//                    onGoogleSignInOnClick = {
//                        googleSignIn()
//                    }
//                )
//
//                if (isAppleLoginDialogOpen) {
//                    AppleLoginWebViewDialog(url = "https://www.google.com/") {
//                        isAppleLoginDialogOpen = false
//                    }
//                }
//            }
        }
    }

    private fun handleKakaoLogin() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (token != null) {

                val kakaoTokens = KakaoTokens(
                    refreshToken = token.refreshToken,
                    accessToken = token.accessToken,
                    idToken = token.idToken
                )
                authViewModel.postKakaoToken(kakaoTokens)
                Log.d(
                    "AuthViewModel",
                    "로그인 성공 refresh = ${token.refreshToken}\naccess = ${token.accessToken}\nid = ${token.idToken}"
                )
            } else if (error != null) {
                Log.d("AuthViewModel", "로그인 실패 + $error")
            }
        }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
            .requestId()
            .requestServerAuthCode(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val googleIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(googleIntent)
    }

    private fun getDynamicLink() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if (it != null) {
                    lifecycleScope.launch {
                        deepLink = it.link
                        val code = deepLink?.getQueryParameters("invite_code")
                        code?.get(0)?.let { c ->
                            invitationViewModel.setInviteCode(code = c)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("dynamicLinkTest", it.toString())
            }
    }
}
