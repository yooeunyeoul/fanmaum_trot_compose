package com.trotfan.trot.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import com.trotfan.trot.R
import com.trotfan.trot.ui.login.AppleLoginWebViewDialog
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
            var isAppleLoginDialogOpen by rememberSaveable { mutableStateOf(false) }
            Surface {
                LoginScreen(
                    onKakaoSignInOnClick = {
                        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                            if (error != null) {
                                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                            } else if (token != null) {
                                Toast.makeText(this, token.accessToken, Toast.LENGTH_SHORT).show()
                            }
                        }
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

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestId()
            .requestServerAuthCode(getString(R.string.server_client_id))
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
            Log.w("googleSignIn", "signInResult:success code=" + account.serverAuthCode + "//id token - " + account.idToken + "//id token" + account.id)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("googleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
}
