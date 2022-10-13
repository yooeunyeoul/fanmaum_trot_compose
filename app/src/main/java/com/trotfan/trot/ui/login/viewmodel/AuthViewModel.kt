package com.trotfan.trot.ui.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _serverAvailable = MutableStateFlow(true)
    val serverAvailable: StateFlow<Boolean>
        get() = _serverAvailable

    private val _userToken = MutableStateFlow<UserToken?>(null)
    val userToken: StateFlow<UserToken?>
        get() = _userToken

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    init {
        getServerState()
    }

    fun getServerState() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getServerStateService()
            }.onSuccess { state ->
                if (state.isAvailable) {
                    Log.d("AuthViewModel", state.isAvailable.toString())
                    context.userIdStore.data.collect {
                        if (it.userId.toInt() != 0) {
                            getUserInfo(it.userId)
                        }
                    }
                } else {
                    //서버 점검
                    _serverAvailable.emit(false)
                }
            }
        }
    }

    fun googleLogin(completedTask: Task<GoogleSignInAccount>) {
        viewModelScope.launch() {
            postGoogleToken(handleGoogleLogin(completedTask))
        }
    }

    fun postGoogleToken(handleGoogleLogin: String?) {
        viewModelScope.launch() {
            kotlin.runCatching {
                handleGoogleLogin.let { repository.postGoogleLogin(GoogleToken(it)) }
            }.onSuccess {
                val userToken = it.data
                Log.d("AuthViewModel", userToken.access_token)
                _userToken.emit(userToken)
                setUserToken(userToken.access_token)
                setUserId(userToken.user_id)
            }.onFailure {
                Log.d("AuthViewModel", it.toString())
            }
        }
    }

    fun postAppleToken(token: AppleToken) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.postAppleLogin(token)
            }.onSuccess {
                val userToken = it.data
                _userToken.emit(userToken)
                setUserToken(userToken.access_token)
                setUserId(userToken.user_id)
                Log.d("AuthViewModel", userToken.access_token)
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
            }
        }
    }

    fun postKakaoToken(kakaoTokens: KakaoTokens) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.postKakaoLogin(kakaoTokens)
            }.onSuccess {
                val userToken = it.data
                _userToken.emit(userToken)
                setUserToken(userToken.access_token)
                setUserId(userToken.user_id)
                Log.d("AuthViewModel", userToken.access_token)
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
            }
        }
    }

    fun getUserInfo(userId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getUserInfo(userId.toInt())
            }.onSuccess {
                val userInfo = it.data
                _userInfo.emit(userInfo)
                Log.d("AuthViewModel", userInfo.toString())
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
            Log.d("setUserToken", "setUserToken")
            it.toBuilder().setAccessToken(token).build()
        }
    }

    suspend fun setUserId(userId: Int) {
        context.userIdStore.updateData {
            Log.d("setUserId", userId.toString())
            it.toBuilder().setUserId(userId.toLong()).build()
        }
    }
}