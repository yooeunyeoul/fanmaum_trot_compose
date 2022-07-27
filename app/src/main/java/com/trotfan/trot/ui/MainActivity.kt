package com.trotfan.trot.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Surface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.R
import com.trotfan.trot.ui.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleSignInLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d("googleSignIn", it.toString())
        if (it.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                LoginScreen(
                    onKakaoSignInOnClick = {
                        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                            if (error != null) {
                                Log.e("Kakao Login", error.toString())
                            } else if (token != null) {
                                Log.e("Kakao Login", "로그인 성공 ${token.accessToken}")
                            }
                        }
                    },
                    onAppleSignInOnClick = {},
                    onGoogleSignInOnClick = {
                        googleSignIn()
                    }
                )
            }
        }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val googleIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(googleIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Log.w("googleSignIn", "signInResult:success code=" + account.idToken)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("googleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
}
