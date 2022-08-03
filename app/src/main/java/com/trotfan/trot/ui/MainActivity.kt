package com.trotfan.trot.ui

import android.content.Intent
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.R
import com.trotfan.trot.ui.login.AppleLoginWebViewDialog
import com.trotfan.trot.ui.login.AuthViewModel
import com.trotfan.trot.ui.login.LoginScreen
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
                    authViewModel,
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
}
