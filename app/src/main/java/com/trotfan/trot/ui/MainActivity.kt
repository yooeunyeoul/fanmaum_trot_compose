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
import com.trotfan.trot.viewmodel.AuthViewModel
import com.trotfan.trot.viewmodel.InvitationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val invitationViewModel: InvitationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDynamicLink()
        setContent {
            FanwooriApp(
            )
        }
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
