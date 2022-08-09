package com.trotfan.trot.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.userTokenStore
import com.trotfan.trot.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    val googleToken = MutableStateFlow<String?>(null)
    private val context = getApplication<Application>()

    fun googleLogin(completedTask: Task<GoogleSignInAccount>) {
        viewModelScope.launch(Dispatchers.IO) {
            googleToken.emit(handleGoogleLogin(completedTask))
            postGoogleToken()
        }
    }

    fun postGoogleToken() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                googleToken.value?.let { repository.postGoogleLogin(GoogleToken(it)) }
            }.onSuccess {
                it?.let { it1 -> setUserToken(it1.access_token) }
                Log.d("AuthViewModel", it?.access_token.toString())
            }.onFailure {
                Log.d("AuthViewModel", it.toString())
            }
        }
    }

    fun postKakaoToken(kakaoTokens: KakaoTokens) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.postKakaoLogin(kakaoTokens)
            }.onSuccess {
                setUserToken(it.access_token)
                Log.d("AuthViewModel", it.access_token)
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
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

    suspend fun setUserToken(token: String) {
        context.userTokenStore.updateData {
            it.toBuilder().setAccessToken(token).build()
        }
    }

}