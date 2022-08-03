package com.trotfan.trot.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val context by lazy { application.applicationContext }

    val kakaoToken = MutableStateFlow<String?>(null)
    val googleToken = MutableStateFlow<String?>(null)

    fun kakaoLogin() {
        viewModelScope.launch {
            kakaoToken.emit(handleKakaoLogin())
        }
    }

    fun googleLogin(completedTask: Task<GoogleSignInAccount>) {
        viewModelScope.launch {
            googleToken.emit(handleGoogleLogin(completedTask))
        }
    }

    private suspend fun handleKakaoLogin(): String? = suspendCoroutine { continuation ->
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (token != null) {
                Log.d("AuthViewModel", "로그인 성공 ${token.accessToken}")
                continuation.resume(token.accessToken)
            } else if (error != null) {
                Log.d("AuthViewModel", "로그인 실패 + $error")
                continuation.resume(null)
            }
        }
    }

    private suspend fun handleGoogleLogin(completedTask: Task<GoogleSignInAccount>): String? =
        suspendCoroutine { continuation ->
            try {
                val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
                Log.d("AuthViewModel", "로그인 성공 ${account.serverAuthCode}")
                continuation.resume(account.serverAuthCode)
            } catch (e: ApiException) {
                Log.d("AuthViewModel", "로그인 실패 ${e.statusCode}")
                continuation.resume(null)
            }
        }
}