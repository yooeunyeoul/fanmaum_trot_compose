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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.ui.login.AppleLoginWebViewDialog
import com.trotfan.trot.ui.login.LoginScreen
import com.trotfan.trot.viewmodel.AuthViewModel
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.R
import com.trotfan.trot.model.userTokenStore
import com.trotfan.trot.ui.signup.SearchStarScreen
import com.trotfan.trot.ui.signup.SettingNicknameScreen
import com.trotfan.trot.ui.theme.FanwooriTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

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
        setContent {
            var isAppleLoginDialogOpen by rememberSaveable { mutableStateOf(false) }
            Surface {
                LoginScreen(
                    onKakaoSignInOnClick = {
                        handleKakaoLogin()
                    },
                    onAppleSignInOnClick = {
                        isAppleLoginDialogOpen = !isAppleLoginDialogOpen
                    },
                    onGoogleSignInOnClick = {
                        googleSignIn()
                    }
                )

                if (isAppleLoginDialogOpen) {
                    AppleLoginWebViewDialog(url = "https://www.google.com/") {
                        isAppleLoginDialogOpen = false
                    }
                }
            }
        }
    }

    fun handleKakaoLogin() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (token != null) {

                val kakaoTokens = KakaoTokens(
                    refreshToken = token.refreshToken,
                    accessToken = token.accessToken,
                    idToken = token.idToken
                )
                authViewModel.postKakaoToken(kakaoTokens)
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

    suspend fun setUserToken(token: String) {
        userTokenStore.updateData {
            it.toBuilder().setAccessToken(token).build()
        }
    }

    private fun dynamicLinkTest() {
        val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://www.fanwoori.com/?test=123123&name=Jone")
            domainUriPrefix = getString(R.string.dynamic_link_url)
            androidParameters(packageName) {}
            iosParameters("com.trotfan.trotDev") {}
            socialMetaTagParameters {
                title = "Example of a Dynamic Link"
                description = "Ths link works whether the app is installed or not!"
            }
        }.addOnSuccessListener {
            Log.d("dynamicLinkTest", it.shortLink.toString())
        }
    }

    private fun getDynamicLinkTest() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if (it != null) {
                    deepLink = it.link
                    val test = deepLink?.getQueryParameters("test")
                    val name = deepLink?.getQueryParameters("name")
                    Log.d("dynamicLinkTest", "${test?.get(0)} // ${name?.get(0)}")
                }
            }
            .addOnFailureListener {
                Log.d("dynamicLinkTest", it.toString())
            }
    }
}
